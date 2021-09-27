package com.tencent.nft.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.nft.common.util.MoneyUtil;
import com.tencent.nft.common.util.UUIDUtil;
import com.tencent.nft.common.util.WxPayUtil;
import com.tencent.nft.core.config.WxGroupConfig;
import com.tencent.nft.entity.nft.NFTProduct;
import com.tencent.nft.entity.pay.PayRequestDTO;
import com.tencent.nft.entity.pay.TradeInfo;
import com.tencent.nft.entity.pay.bo.PayDetailBO;
import com.tencent.nft.entity.pay.bo.PrepayBO;
import com.tencent.nft.mapper.NftProductMapper;
import com.tencent.nft.mapper.TradeMapper;
import com.tencent.nft.service.IPayService;
import com.tencent.nft.service.handler.WechatPayHandler;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

/**
 * @author: imhuis
 * @date: 2021/9/27
 * @description:
 */
@Service
public class PayServiceImpl implements IPayService {

    @Resource
    private NftProductMapper productMapper;

    @Resource
    private TradeMapper tradeMapper;

    @Autowired
    private WechatPayHandler payHandler;

    @Autowired
    private WxGroupConfig wxGroupConfig;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public PrepayBO prePay(PayRequestDTO dto) throws Exception {
        // 首先判断是否有购买资格，已经预约，或者购买买过一次的不能购买第二次

        final String tradeNo = UUIDUtil.generateUUID();
        PayDetailBO payDetailBO = createPayDetailBO(tradeNo, dto);
        System.out.println(payDetailBO.getTotal());
        // 生成prepay_id
        String prepayId = payHandler.handler(payDetailBO);

        // 异步插入数据表
//        createOrder(payDetailBO);

        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        final String nonceStr = RandomUtil.randomString(32);
        final String packages = "prepay_id=" + prepayId;

        StringBuilder sb = new StringBuilder();
        sb.append(wxGroupConfig.getAppletAppId() + "\n")
                .append(timestamp + "\n")
                .append(nonceStr + "\n")
                .append(packages + "\n");
        String result = sb.toString();

        ClassPathResource classPathResource = new ClassPathResource("pay/apiclient_key.pem");
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(classPathResource.getInputStream());

        String sign = WxPayUtil.getPaySign(result, merchantPrivateKey);

        // 小程序调起支付API
        PrepayBO prepayBO = new PrepayBO();
        prepayBO.setAppId(wxGroupConfig.getAppId());
        prepayBO.setTimeStamp(timestamp);
        prepayBO.setNonceStr(nonceStr);
        prepayBO.setPrepayId(prepayId);
        prepayBO.setPackageStr(packages);
        prepayBO.setSignType("RSA");
        prepayBO.setPaySign(sign);
        return prepayBO;
    }

    private PayDetailBO createPayDetailBO(String tradeNo, PayRequestDTO dto) {
        // 查询商品信息
        NFTProduct product = productMapper.selectByNftId(dto.getNftId()).get();
        PayDetailBO payDetailBO = new PayDetailBO();
        payDetailBO.setTradeNo(tradeNo);
        payDetailBO.setOpenId(dto.getOpenId());
        payDetailBO.setTotal(MoneyUtil.yuan2fen(product.getUnitPrice()));
        payDetailBO.setDescription("数字藏品-" + dto.getNftId());
        return payDetailBO;
    }

    @Override
    public void notifyApp(String resJson) {
        // 读取json树
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(resJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String eventType = jsonNode.get("event_type").asText();
        /**
         * {
         *     "id": "EV-2018022511223320873",
         *     "create_time": "2015-05-20T13:29:35+08:00",
         *     "resource_type": "encrypt-resource",
         *     "event_type": "TRANSACTION.SUCCESS",
         *     "summary": "支付成功",
         *     "resource": {
         *         "original_type": "transaction",
         *         "algorithm": "AEAD_AES_256_GCM",
         *         "ciphertext": "",
         *         "associated_data": "",
         *         "nonce": ""
         *     }
         * }
         */
        if (eventType.equals("TRANSACTION.SUCCESS")){
            JsonNode childField = jsonNode.get("resource");
            String nonce = childField.get("nonce").asText();
            String associatedData = childField.get("associated_data").asText();
            String ciphertext = childField.get("ciphertext").asText();
            String resourceJson = WxPayUtil.decryptNotifyV3(associatedData, nonce, ciphertext, wxGroupConfig.getApiKey());

            JsonNode resource = jsonNode.get("resource");
            TradeInfo tradeInfo = new TradeInfo();
            tradeInfo.setTradeNo(resource.get("out_trade_no").asText());
            tradeInfo.setTransactionId(resource.get("transaction_id").asText());
            tradeInfo.setAmount(resource.at("/amount/total").asInt());
            tradeInfo.setPayer(resource.get("/payer/openid").asText());
//            DateTimeFormatter dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            tradeInfo.setSuccessTime(LocalDateTime.parse(resource.get("success_time").asText()));
            tradeMapper.insert(tradeInfo);
        }

    }


    @Async
    public void createOrder(PayDetailBO payDetailBO){
        // 默认新增状态为无效订单
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setTradeNo(payDetailBO.getTradeNo());
        // 分 >> 元
        tradeInfo.setAmount(payDetailBO.getTotal());
        tradeInfo.setDescription(payDetailBO.getDescription());
        tradeInfo.setPayer(payDetailBO.getOpenId());
        tradeMapper.insert(tradeInfo);
    }

    public static void main(String[] args) {
        System.out.println(MoneyUtil.yuan2fen(new BigDecimal("0.01")));
    }
}
