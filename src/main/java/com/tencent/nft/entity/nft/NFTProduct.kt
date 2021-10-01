package com.tencent.nft.entity.nft

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

open class NFTProduct : SuperNFT(), Serializable {

    // 单价
    @JsonProperty("price")
    var unitPrice: BigDecimal? = null

    // 发行量
    @JsonProperty("fxl")
    var circulation: Int? = null

    // 预约开始时间
    var reserveStartTime: LocalDateTime? = null

    // 预约结束时间
    var reserveEndTime: LocalDateTime? = null

    // 开售开始时间
    var sellStartTime: LocalDateTime? = null

    // 开售结束时间
    var sellEndTime: LocalDateTime? = null

}