package com.tencent.nft.entity.nft.vo

import com.tencent.nft.entity.nft.NFTInfo
import java.io.Serializable

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description: NFT列表展示VO
 */
class NFTListVO : NFTInfo(), Serializable {

    // 销售总额
    var totalSales: Long? = null

    // 销售总量
    var totalAmount = 0
}