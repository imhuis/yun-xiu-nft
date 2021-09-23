package com.tencent.nft.entity.nft

import com.tencent.nft.common.enums.NFTSaleStatusEnum
import com.tencent.nft.common.enums.NFTStatusEnum
import java.io.Serializable
import java.time.LocalDateTime

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description: 子NFT
 */
open class SubNFT : SuperNFT(), Serializable {

    // 对于子nft，nft id是当前nft编号
    override var nftId: String?= null

    var superNFTId: String?= null

    // NFT出售状态状态 (子属性 )
    var saleStatus: NFTSaleStatusEnum? = null

    // 区块链地址
    var address: String? = null

    // 售出时间
    var soldTime: LocalDateTime? = null

    // 子NFT创建时间
//    override var nftCreateTime: LocalDateTime? = null
}