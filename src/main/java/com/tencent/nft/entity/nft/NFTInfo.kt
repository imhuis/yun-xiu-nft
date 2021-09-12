package com.tencent.nft.entity.nft

import java.io.Serializable
import java.time.LocalDateTime

open class NFTInfo : SuperNFT(), Serializable {

    // 单价
    var unitPrice: Int? = null

    // 发行量
    var circulation = 0

    // 预约开始时间
    open var reserveStartTime: LocalDateTime? = null

    // 预约结束时间
    open var reserveEndTime: LocalDateTime? = null

    // 开售开始时间
    open var sellStartTime: LocalDateTime? = null

    // 开售结束时间
    open var sellEndTime: LocalDateTime? = null

    // 预约人数
    var reservation = 0

}