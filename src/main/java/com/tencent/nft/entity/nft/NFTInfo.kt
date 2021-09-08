package com.tencent.nft.entity.nft

import java.io.Serializable
import java.time.LocalDateTime

open class NFTInfo : SuperNFT(), Serializable {

    // 单价
    var unitPrice: Int? = null

    // 发行量
    var circulation = 0

    // 预约开始时间
    var reserveStartTime: LocalDateTime? = null

    // 预约结束时间
    var reserveEndTime: LocalDateTime? = null

    // 开售开始时间
    var sellStartTime: LocalDateTime? = null

    // 开售结束时间
    var sellEndTime: LocalDateTime? = null

    var reservation = 0

}