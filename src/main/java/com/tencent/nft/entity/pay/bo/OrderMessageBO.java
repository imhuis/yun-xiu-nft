package com.tencent.nft.entity.pay.bo;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/9/29
 * @description:
 */
public class OrderMessageBO implements Serializable {

    private String tradeNo;
    private String openId;
    private String productId;

    public OrderMessageBO(String tradeNo, String openId, String productId) {
        this.tradeNo = tradeNo;
        this.openId = openId;
        this.productId = productId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
