package com.tencent.nft.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author zwq
 * @version 2.0
 * @date 2020-4-11 10:29:06
 */
@Configuration
@PropertySource("classpath:properties/wx-pay.properties")
@ConfigurationProperties(prefix = "wx.pay", ignoreUnknownFields = true)
public class WxGroupProperties {

    // 小程序APP_ID
    private String appletAppId;
    // 微信支付 APP的APP_ID
    private String appId;
    // 微信支付 key
    private String wxPayKey;
    // 微信支付 商户id
    private String wxPayMchId;
    private String serialNumber;
    // 微信支付 支付回调地址
    private String callBack;
    // api v3 key
    private String apiKey;

    public String getAppletAppId() {
        return appletAppId;
    }

    public void setAppletAppId(String appletAppId) {
        this.appletAppId = appletAppId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getWxPayKey() {
        return wxPayKey;
    }

    public void setWxPayKey(String wxPayKey) {
        this.wxPayKey = wxPayKey;
    }

    public String getWxPayMchId() {
        return wxPayMchId;
    }

    public void setWxPayMchId(String wxPayMchId) {
        this.wxPayMchId = wxPayMchId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCallBack() {
        return callBack;
    }

    public void setCallBack(String callBack) {
        this.callBack = callBack;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
