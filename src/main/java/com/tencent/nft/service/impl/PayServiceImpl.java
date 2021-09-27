package com.tencent.nft.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.tencent.nft.common.util.SignUtil;
import com.tencent.nft.common.util.UUIDUtil;
import com.tencent.nft.core.config.WxGroupConfig;
import com.tencent.nft.entity.pay.PayRequestDTO;
import com.tencent.nft.entity.pay.TradeInfo;
import com.tencent.nft.entity.pay.bo.PayDetailBO;
import com.tencent.nft.entity.pay.bo.PrepayBO;
import com.tencent.nft.mapper.TradeMapper;
import com.tencent.nft.service.IPayService;
import com.tencent.nft.service.handler.WechatPayHandler;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.PrivateKey;
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
    private TradeMapper tradeMapper;

    @Autowired
    private WechatPayHandler payHandler;

    @Autowired
    private WxGroupConfig wxGroupConfig;

    @Override
    public PrepayBO prePay(PayRequestDTO dto) throws Exception {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
//        String dateStr = dtf.format(LocalDateTime.now());
        final String tradeNo = UUIDUtil.generateUUID();
        PayDetailBO payDetailBO = new PayDetailBO();
        payDetailBO.setTotal(1);
        payDetailBO.setDescription("数字藏品-" + dto.getNftId());
        payDetailBO.setTradeNo(tradeNo);
        payDetailBO.setOpenId(dto.getOpenId());
        String prepayId = payHandler.handler(payDetailBO);

        // 异步插入数据表
//        createOrder(payDetailBO);

        // 处理下单结果内容，生成前端调起微信支付的sign
        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        final String nonceStr = RandomUtil.randomString(32);
        final String packages = "prepay_id=" + prepayId;

        Map<String, String> wxPayMap = new HashMap<>();
        wxPayMap.put("appId", wxGroupConfig.getAppletAppId());
        wxPayMap.put("timeStamp", timestamp);
        wxPayMap.put("nonceStr", nonceStr);
        wxPayMap.put("package", packages);

        StringBuilder sb = new StringBuilder();
        sb.append(wxGroupConfig.getAppletAppId() + "\n");
        sb.append(timestamp + "\n");
        sb.append(nonceStr + "\n");
        sb.append(packages + "\n");

        ClassPathResource classPathResource = new ClassPathResource("pay/apiclient_key.pem");
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(classPathResource.getInputStream());

        String result = sb.toString();
//        String sign = WXPayUtil.generateSignature2(wxPayMap, wxGroupConfig.getWxPayKey(), SignType.HMACSHA256);
        String sign = SignUtil.getPaySign(result, merchantPrivateKey);
        System.out.println("sign" + sign);

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

}
