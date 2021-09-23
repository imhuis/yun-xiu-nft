package com.tencent.nft.entity.nft

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

/**
 * @author: imhuis
 * @date: 2021/9/23
 * @description: 预约情况
 */
class ReservationInfo : Serializable {

    // NFT编号
    var nftId: String? = null

    @JsonProperty("yyrs")
    var reservation = 0
}