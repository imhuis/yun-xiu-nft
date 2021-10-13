package com.tencent.nft.entity.pay;

import com.tencent.nft.entity.BaseEntity;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/10/13
 * @description: 预订单表
 */
public class PreviouslyOrder extends BaseEntity implements Serializable {

    // 系统内部业务流水
    private String tradeNo;

    // 商品编号
    private String productNo;

    // 单价
    private int price = 0;

    // 支付人（openid）
    private String payer;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }
}
