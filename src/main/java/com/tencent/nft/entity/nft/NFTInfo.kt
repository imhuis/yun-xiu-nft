package com.tencent.nft.entity.nft

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.time.LocalDateTime

open class NFTInfo : SuperNFT(), Serializable {

    // 封面图片
    var coverPicture: String? = null

    // 详细介绍图片
    var detailPicture: String? = null

    // 预约人数
    @JsonProperty("yyrs")
    var reservation = 0

}