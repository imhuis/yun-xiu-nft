package com.tencent.nft.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.nft.common.enums.pay.TradeState;
import com.tencent.nft.common.util.MoneyUtil;
import com.tencent.nft.common.util.UUIDUtil;
import com.tencent.nft.common.util.WxPayUtil;
import com.tencent.nft.core.config.RabbitmqConfig;
import com.tencent.nft.common.properties.WxGroupProperties;
import com.tencent.nft.entity.nft.NFTProduct;
import com.tencent.nft.entity.pay.dto.PayRequestDTO;
import com.tencent.nft.entity.pay.TradeInfo;
import com.tencent.nft.entity.pay.bo.PayDetailBO;
import com.tencent.nft.entity.pay.bo.PrepayBO;
import com.tencent.nft.mapper.NftProductMapper;
import com.tencent.nft.mapper.TradeMapper;
import com.tencent.nft.mapper.UserLibraryMapper;
import com.tencent.nft.service.IPayService;
import com.tencent.nft.service.handler.WechatPayHandler;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.PrivateKey;
import java.time.Instant;

/**
 * @author: imhuis
 * @date: 2021/9/27
 * @description:
 */
@Service
public class PayServiceImpl implements IPayService {

    final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Resource
    private TradeMapper tradeMapper;

    @Autowired
    private WechatPayHandler payHandler;

    @Autowired
    private WxGroupProperties wxGroupProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Resource
    private NftProductMapper productMapper;

    @Resource
    private UserLibraryMapper libraryMapper;

    @Override
    public PrepayBO prePay(PayRequestDTO dto) throws Exception {
        // 首先判断是否有购买资格，已经预约，或者购买买过一次的不能购买第二次

        final String tradeNo = UUIDUtil.generateUUID();
        PayDetailBO payDetailBO = createPayDetailBO(tradeNo, dto);
        System.out.println(payDetailBO.getTotal());
        // 生成prepay_id
        String prepayId = payHandler.handler(payDetailBO);

        // 生产预订单。插入数据表
        createOrder(payDetailBO);

        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        final String nonceStr = RandomUtil.randomString(32);
        final String packages = "prepay_id=" + prepayId;

        StringBuilder sb = new StringBuilder();
        sb.append(wxGroupProperties.getAppletAppId() + "\n")
                .append(timestamp + "\n")
                .append(nonceStr + "\n")
                .append(packages + "\n");
        String result = sb.toString();

        ClassPathResource classPathResource = new ClassPathResource("pay/apiclient_key.pem");
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(classPathResource.getInputStream());

        String sign = WxPayUtil.getPaySign(result, merchantPrivateKey);

        // 小程序调起支付API
        PrepayBO prepayBO = new PrepayBO();
        prepayBO.setAppId(wxGroupProperties.getAppId());
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

        /**
         * {
         *     "id": "311fe73f-c397-5e5a-9c8f-9fc94afb387b",
         *     "create_time": "2021-09-28T13:50:48+08:00",
         *     "resource_type": "encrypt-resource",
         *     "event_type": "TRANSACTION.SUCCESS",
         *     "summary": "支付成功",
         *     "resource": {
         *         "original_type": "transaction",
         *         "algorithm": "AEAD_AES_256_GCM",
         *         "ciphertext": "Ay25zutpHN+uMAaO6mqOeZvePpU3VR7wfY4GWlbI+bifz58DbR0BSrpI+kAmE/WnMbgwG5rBQKPzjZcfCIPILVGUMWMaPnGI7CzQ2RHuMXUzGugYH4iJZOjYVmxEJDB3J1OAPJoZC5Xu7b8aFrFbY/WqQ+pUWZnfBEkdyQPx7yHU06b8UEtYZjlbyPPP4XGe42DM6nuWiOmVvDxPtAO4XyZKqzW6WteLzHbF/kqa8f5VzkhXsJV25ytfV9/JM7/KW3nLOgz6KWfsK3K4UmlExK5OewSTGiTjM6pApyXuBSBLSMsI6efhQ6RVRcfcWs8lyDIXsc1BxW+Axvv4Y5NGfsSbBoxF1NhJT+8EScFwrMdOcO2prPFtLzNTsxjsPYTd/OGA2c1mgImjsJBMhSe/vkZW0bmXXsU/M/EBpzu72aXHbj/yVhjtJeG9k/RGT7ez7Z0m3+nIaViareifSm//IG/pHzAtCrULK+npNNHrvUajFKum4gYSMksiSq5RRmvQIs+vzg0DvsZXAP19w02/uNV8G5SefTXk5q5Z+JM5dYaWM7sP0TNDkWHssD9j0xZlnhZTWsBM/7Vmw3R0nvZR",
         *         "associated_data": "transaction",
         *         "nonce": "HipFwys1ToyA"
         *     }
         * }
         */
        // 读取json树
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(resJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        /**
         *
         *  {
         *     "mchid": "1503971171",
         *     "appid": "wxb3982d59b8a5e644",
         *     "out_trade_no": "751b1db31b6240748aefc580cd674307",
         *     "transaction_id": "4200001121202109282704065018",
         *     "trade_type": "JSAPI",
         *     "trade_state": "SUCCESS",
         *     "trade_state_desc": "支付成功",
         *     "bank_type": "OTHERS",
         *     "attach": "",
         *     "success_time": "2021-09-28T13:50:48+08:00",
         *     "payer": {
         *         "openid": "oQ13L5K2HVK3rLYcIlw_n2va7Tcs"
         *     },
         *     "amount": {
         *         "total": 1,
         *         "payer_total": 1,
         *         "currency": "CNY",
         *         "payer_currency": "CNY"
         *     }
         * }
         *
         */

        String eventType = jsonNode.get("event_type").asText();
        // 支付成功
        if (eventType.equals("TRANSACTION.SUCCESS")){
            JsonNode childField = jsonNode.get("resource");
            String nonce = childField.get("nonce").asText();
            String associatedData = childField.get("associated_data").asText();
            String ciphertext = childField.get("ciphertext").asText();
            String resourceJson = WxPayUtil.decryptNotifyV3(associatedData, nonce, ciphertext, wxGroupProperties.getApiKey());

            JsonNode tradeDetail = null;
            try {
                tradeDetail = objectMapper.readTree(resourceJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            logger.info("resource \n {}", tradeDetail.toString());

            // 外部订单号码
            String outTradeNo = tradeDetail.get("out_trade_no").asText();

            TradeInfo tradeInfo = tradeMapper.selectByTradeNo(outTradeNo);
            if (tradeInfo.getTradeStatus() == TradeState.SUCCESS){
                return;
            }
            tradeInfo = new TradeInfo();
            tradeInfo.setTradeNo(outTradeNo);
            tradeInfo.setTransactionId(tradeDetail.get("transaction_id").asText());
            // 设置价格，单位为分
            tradeInfo.setAmount(tradeDetail.at("/amount/total").asInt());
            tradeInfo.setPayerTotal(tradeDetail.at("/amount/payer_total").asInt());
            // 支付人
            tradeInfo.setPayer(tradeDetail.at("/payer/openid").asText());
            tradeInfo.setTradeStatus(TradeState.SUCCESS);
            tradeMapper.updateByTradeNo(tradeInfo);

//            tradeMapper.insert(tradeInfo);
            // 发送消息

            amqpTemplate.convertAndSend(RabbitmqConfig.DEFAULT_EXCHANGE_NAME, RabbitmqConfig.ON_CHAIN_ROUTE_KEY, "用户购买成功 >> 执行后续操作");
        }

    }

    @Transactional
    @Async
    void updateUserLibrary() {

        // 更新数量
//        productMapper.updateStockByProductId();
//        libraryMapper.selectNftIdByPhone();

    }


    @Async
    public void createOrder(PayDetailBO payDetailBO){
        // 默认新增状态为未付款状态订单
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setTradeNo(payDetailBO.getTradeNo());
        tradeInfo.setTradeStatus(TradeState.NOTPAY);
        // 分 >> 元
        tradeInfo.setAmount(payDetailBO.getTotal());
        tradeInfo.setDescription(payDetailBO.getDescription());
        tradeInfo.setPayer(payDetailBO.getOpenId());
        tradeMapper.insert(tradeInfo);
    }

//    public static void main(String[] args) {
//        System.out.println(MoneyUtil.yuan2fen(new BigDecimal("0.01")));
//    }
}
