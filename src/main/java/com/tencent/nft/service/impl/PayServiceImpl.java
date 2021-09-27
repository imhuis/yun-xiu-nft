package com.tencent.nft.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.tencent.nft.common.util.MoneyUtil;
import com.tencent.nft.common.util.SignUtil;
import com.tencent.nft.common.util.UUIDUtil;
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
import java.math.RoundingMode;
import java.security.PrivateKey;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

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

        String sign = SignUtil.getPaySign(result, merchantPrivateKey);

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
    public void wxNotify() {

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
//        DecimalFormat df = new DecimalFormat("#.00");
//        price = Double.valueOf(df.format(price));
//        int money = (int)(price * 100);
//        return money;

        System.out.println(MoneyUtil.yuan2fen(new BigDecimal("0.01")));
    }
}
