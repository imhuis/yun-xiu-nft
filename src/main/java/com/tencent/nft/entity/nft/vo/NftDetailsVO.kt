package com.tencent.nft.entity.nft.vo

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.tencent.nft.entity.nft.SubNFT
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * @author: imhuis
 * @date: 2021/9/12
 * @description: NFT详情vo
 */
@JsonIgnoreProperties(value = ["id", "create_time", "update_time"])
class NftDetailsVO : SubNFT(), Serializable {

    // 单价
    @JsonProperty("price")
    var unitPrice: Double? = null

    // 发行量
    @JsonProperty("fxl")
    var circulation: Int? = null

    // 封面图片
    var coverPicture: String? = null

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    var reserveStartTime: LocalDateTime? = null

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
//    var reserveEndTime: LocalDateTime? = null

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    var sellStartTime: LocalDateTime? = null

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
//    var sellEndTime: LocalDateTime? = null

    // 销售总量
    var totalAmount:Int? = null

    // 销售总额
    var totalSales:Long? = null

    // 预约人数
    @JsonProperty("yyrs")
    var reservation:Long? = null
}