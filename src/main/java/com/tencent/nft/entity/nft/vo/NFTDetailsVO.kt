package com.tencent.nft.entity.nft.vo

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.tencent.nft.entity.nft.NFTInfo
import com.tencent.nft.entity.nft.NFTProduct
import java.io.Serializable
import java.time.LocalDateTime

/**
 * @author: imhuis
 * @date: 2021/9/12
 * @description:
 */
class NFTDetailsVO : NFTProduct(), Serializable {

    // 预约开始时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    override var reserveStartTime: LocalDateTime? = null

    // 预约结束时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    override var reserveEndTime: LocalDateTime? = null

    // 开售开始时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    override var sellStartTime: LocalDateTime? = null

    // 开售结束时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    override var sellEndTime: LocalDateTime? = null

    // 销售总量
    var totalAmount:Int? = null

    // 销售总额
    var totalSales: Long? = null

    // 预约人数
    @JsonProperty("yyrs")
    var reservation = 0

}