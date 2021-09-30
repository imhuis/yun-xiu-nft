package com.tencent.nft.entity.pay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/9/26
 * @description:
 */
public class PayRequestDTO implements Serializable {

    // 商品id
    @NotNull(message = "商品id不能为空")
    @JsonProperty("nft_id")
    private String productId;

    // 价格
    @DecimalMin(value = "0.01",message = "最低金额0.01")
    private double price;

    // 支付者
    private String openId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
                "productId='" + productId + '\'' +
                ", price=" + price +
                ", openId='" + openId + '\'' +
                '}';
    }
}
