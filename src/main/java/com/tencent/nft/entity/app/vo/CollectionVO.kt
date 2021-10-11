package com.tencent.nft.entity.app.vo

import java.io.Serializable

/**
 * @author: imhuis
 * @date: 2021/9/24
 * @description:
 */
class CollectionVO : Serializable {

    var nftName: String? = null
    var nftId: String? = null
    var nftFile: String? = null
    var fileType: Int? = null
    var issuer: String? = null
    var brandOwner: String? = null
    var coverPic: String? = null
    var price = 0.0
    var blockChainAddress: String? = null
    var owner: String? = null
    var circulation: Int? =null
}