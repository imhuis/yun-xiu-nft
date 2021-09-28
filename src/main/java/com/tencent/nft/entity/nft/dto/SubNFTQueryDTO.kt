package com.tencent.nft.entity.nft.dto

import com.fasterxml.jackson.annotation.JsonAlias
import java.io.Serializable

/**
 * @author: imhuis
 * @date: 2021/9/12
 * @description: NFT子列表查询条件DTO
 */
class SubNFTQueryDTO : Serializable {

    @JsonAlias("sub_id")
    var id: String? = null

    @JsonAlias("status")
    var saleStatus: Int? = null
}