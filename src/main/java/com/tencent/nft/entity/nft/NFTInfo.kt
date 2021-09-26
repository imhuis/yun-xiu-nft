package com.tencent.nft.entity.nft

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonView
import java.io.Serializable

@JsonIgnoreProperties(value = ["id", "create_time", "update_time"])
open class NFTInfo : SuperNFT(), Serializable {

    interface Base

    // 封面图片
    @JsonView(Base::class)
    var coverPicture: String? = null

    // 详细介绍图片
    var detailPicture: String? = null

}