package com.tencent.nft.entity.pay;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/9/26
 * @description:
 */
public class PrepayBO implements Serializable {

    // 小程序id-必填
    private String appId;
    // 时间戳-必填
    private String timeStamp;
    // 随机字符串-必填
    private String nonceStr;
    // 订单详情扩展字符串-必填
    private String prepayId;
    // 签名方式-必填
    private String signType;
    // 签名-必填
    private String paySign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }
}
