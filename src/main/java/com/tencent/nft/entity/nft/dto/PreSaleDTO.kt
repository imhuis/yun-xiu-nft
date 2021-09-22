package com.tencent.nft.entity.nft.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.time.LocalDateTime
import javax.validation.constraints.NotNull

/**
 * @author: imhuis
 * @date: 2021/9/22
 * @description:
 */
class PreSaleDTO : Serializable {

    // 单价
    @JsonProperty("price")
    @NotNull
    var unitPrice: Int? = null

    // 发行量
    @JsonProperty("fxl")
    @NotNull
    var circulation: Int? = null

    // 预约开始时间
    @NotNull
    var reserveStartTime: LocalDateTime? = null

    // 预约结束时间
    @NotNull
    var reserveEndTime: LocalDateTime? = null

    // 开售开始时间
    @NotNull
    var sellStartTime: LocalDateTime? = null

    // 开售结束时间
    @NotNull
    var sellEndTime: LocalDateTime? = null
}