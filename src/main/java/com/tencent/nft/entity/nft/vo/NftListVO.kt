package com.tencent.nft.entity.nft.vo

import com.tencent.nft.entity.nft.SuperNFT
import java.io.Serializable

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description: NFT列表展示VO
 */
class NftListVO : SuperNFT(), Serializable {

    // 单价
    var unitPrice: Double? = null

    // 发行量
    var circulation:Int? = null

    // 销售总量
    var totalAmount:Int? = null

    // 销售总额
    var totalSales: Long? = null
}