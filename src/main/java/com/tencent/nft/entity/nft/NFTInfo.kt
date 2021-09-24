package com.tencent.nft.entity.nft

import java.io.Serializable

open class NFTInfo : SuperNFT(), Serializable {

    // 封面图片
    var coverPicture: String? = null

    // 详细介绍图片
    var detailPicture: String? = null

}