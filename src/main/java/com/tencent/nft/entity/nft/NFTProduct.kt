package com.tencent.nft.entity.nft

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.time.LocalDateTime

open class NFTProduct : SuperNFT(), Serializable {

    // 单价
    @JsonProperty("price")
    var unitPrice: Int? = null

    // 发行量
    @JsonProperty("fxl")
    var circulation: Int? = null

    // 封面图片
    var coverPicture: String? = null

    // 详细介绍
    var detailPicture: String? = null

    // 预约开始时间
    open var reserveStartTime: LocalDateTime? = null

    // 预约结束时间
    open var reserveEndTime: LocalDateTime? = null

    // 开售开始时间
    open var sellStartTime: LocalDateTime? = null

    // 开售结束时间
    open var sellEndTime: LocalDateTime? = null

    // 预约人数
    @JsonProperty("yyrs")
    var reservation = 0

}