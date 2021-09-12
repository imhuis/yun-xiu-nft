package com.tencent.nft.entity.nft.dto

import java.io.Serializable

/**
 * @author: imhuis
 * @date: 2021/9/9
 * @description:
 */
open class NftListQueryDTO : Serializable {

    var nftId: String? = null
    var nftName: String? = null
    var nftType: Int? = null
    var nftStatus: Int? = null

}