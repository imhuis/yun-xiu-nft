package com.tencent.nft.entity.nft.vo

import com.tencent.nft.entity.nft.SuperNFT
import java.io.Serializable

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description: NFT列表展示VO
 */
class NFTListVO : SuperNFT(), Serializable {

    // 单价
    var unitPrice: Int? = null

    // 发行量
    var circulation:Int? = null

    // 销售总量
    var totalAmount:Int? = null

    // 销售总额
    var totalSales: Long? = null
}