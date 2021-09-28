package com.tencent.nft.service.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tencent.nft.common.properties.WxGroupProperties;
import com.tencent.nft.entity.pay.bo.PayDetailBO;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author: imhuis
 * @date: 2021/9/26
 * @description:
 */
@Component
public class WeChatPayHandler {

    final Logger logger = LoggerFactory.getLogger(WeChatPayHandler.class);

    @Autowired
    private HttpClient wechatPayHttpClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WxGroupProperties wxGroupProperties;

    private final String JS_API = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";

    /**
     * jsapi 调起支付
     * 获取 prepayId
     * @param bo
     * @return
     */
    public String handler(PayDetailBO bo) throws IOException {
        HttpPost httpPost = new HttpPost(JS_API);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("mchid", wxGroupProperties.getWxPayMchId())
                .put("appid", wxGroupProperties.getAppletAppId())
                .put("description", bo.getDescription())
                .put("notify_url", wxGroupProperties.getCallBack())
                .put("out_trade_no", bo.getTradeNo());
        rootNode.putObject("amount")
                .put("total", bo.getTotal());
        rootNode.putObject("payer")
                .put("openid", bo.getOpenId());

        objectMapper.writeValue(bos, rootNode);

        httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));

        HttpResponse response = wechatPayHttpClient.execute(httpPost);


        String bodyAsString = EntityUtils.toString(response.getEntity());
        logger.info("wx bodyAsString() \n {}", bodyAsString);
        JSONObject jsonObject = JSONObject.fromObject(bodyAsString);
        String prepayId = (String) jsonObject.get("prepay_id");
        return prepayId;
    }
}
