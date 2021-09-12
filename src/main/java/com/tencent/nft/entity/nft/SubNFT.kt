package com.tencent.nft.entity.nft

import java.io.Serializable
import java.time.LocalDateTime

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description: 子NFT
 */
class SubNFT : SuperNFT(), Serializable {

    override var nftId: String?= null

    var superNFTId: String?= null

    // 区块链地址
    var address: String? = null

    // 售出时间
    var soldTime: LocalDateTime? = null

    // 子NFT创建时间
    override var nftCreateTime: LocalDateTime? = null
}