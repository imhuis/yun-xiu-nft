package com.tencent.nft.entity.pay;

import com.tencent.nft.common.enums.pay.TradeState;
import com.tencent.nft.entity.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: imhuis
 * @date: 2021/9/26
 * @description:
 */
public class TradeInfo extends BaseEntity implements Serializable {

    private String tradeNo;
    // 默认不生效
    private TradeState tradeStatus;
    private String transactionId;
    private String description;
    private int amount;
    private String payer;
    private LocalDateTime successTime;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public TradeState getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(TradeState tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public LocalDateTime getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(LocalDateTime successTime) {
        this.successTime = successTime;
    }
}
