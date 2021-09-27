package com.tencent.nft.entity.nft.vo

import com.tencent.nft.entity.nft.SubNFT
import java.io.Serializable
import java.time.LocalDateTime

/**
 * @author: imhuis
 * @date: 2021/9/12
 * @description:
 */
class SubNFTListVO : SubNFT(), Serializable {

    var unitPrice: Double? = null

    var reserveStartTime: LocalDateTime? = null

    var sellStartTime: LocalDateTime? = null
}