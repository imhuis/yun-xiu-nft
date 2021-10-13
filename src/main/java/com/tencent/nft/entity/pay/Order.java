package com.tencent.nft.entity.pay;

import com.tencent.nft.entity.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: imhuis
 * @date: 2021/10/8
 * @description: 订单表
 */
public class Order extends BaseEntity implements Serializable {

    private String tradeId;
    private String productId;
    private String nftId;
    private BigDecimal amount;
    private String payerOpenId;
    private String payerPhone;

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNftId() {
        return nftId;
    }

    public void setNftId(String nftId) {
        this.nftId = nftId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayerOpenId() {
        return payerOpenId;
    }

    public void setPayerOpenId(String payerOpenId) {
        this.payerOpenId = payerOpenId;
    }

    public String getPayerPhone() {
        return payerPhone;
    }

    public void setPayerPhone(String payerPhone) {
        this.payerPhone = payerPhone;
    }
}
