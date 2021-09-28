package com.tencent.nft.entity.pay.dto;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/9/26
 * @description:
 */
public class PayRequestDTO implements Serializable {

    private String nftId;
    private Double price;
    // 支付者
    private String openId;

    public String getNftId() {
        return nftId;
    }

    public void setNftId(String nftId) {
        this.nftId = nftId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "PayRequestDTO{" +
                "nftId='" + nftId + '\'' +
                ", price=" + price +
                ", openId='" + openId + '\'' +
                '}';
    }
}
