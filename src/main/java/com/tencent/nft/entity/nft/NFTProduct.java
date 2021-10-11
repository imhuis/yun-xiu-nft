package com.tencent.nft.entity.nft;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author: imhuis
 * @date: 2021/10/11
 * @description:
 */
public class NFTProduct extends SuperNFT implements Serializable {

    // 单价
    @JsonProperty("price")
    private BigDecimal unitPrice;

    // 发行量
    @JsonProperty("fxl")
    private int circulation;

    // 预约开始时间
    private LocalDateTime reserveStartTime;

    // 预约结束时间
//    private LocalDateTime reserveEndTime;

    // 开售开始时间
    private LocalDateTime sellStartTime;

    // 开售结束时间
//    private LocalDateTime sellEndTime;

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getCirculation() {
        return circulation;
    }

    public void setCirculation(int circulation) {
        this.circulation = circulation;
    }

    public LocalDateTime getReserveStartTime() {
        return reserveStartTime;
    }

    public void setReserveStartTime(LocalDateTime reserveStartTime) {
        this.reserveStartTime = reserveStartTime;
    }

    public LocalDateTime getSellStartTime() {
        return sellStartTime;
    }

    public void setSellStartTime(LocalDateTime sellStartTime) {
        this.sellStartTime = sellStartTime;
    }
}
