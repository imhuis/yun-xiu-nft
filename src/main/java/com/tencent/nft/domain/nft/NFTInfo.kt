package com.tencent.nft.domain.nft

import java.io.Serializable
import java.time.LocalDateTime

class NFTInfo : SuperNFT(), Serializable {

    // NFT状态
    private val nftStatus: String? = null

    // 单价
    private val unitPrice: Int? = null

    // 发行量
    private val circulation = 0

    // 预约开始时间
    private val reserveTime: LocalDateTime? = null

    // 开售时间
    private val sellTime: LocalDateTime? = null

    // NFT创建时间
    private val ntfCreateTime: LocalDateTime? = null

}