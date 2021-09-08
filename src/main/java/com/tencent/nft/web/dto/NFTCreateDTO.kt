package com.tencent.nft.web.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description: 新增NFT dto
 */
class NFTCreateDTO : Serializable {

    @NotBlank(message = "NFT名称不能为空")
    private val nftName: String? = null
    @NotBlank(message = "发行方名称不能为空")
    private val issuer: String? = null
    private val nftType = 0
    private val nftFile: String? = null
    private val coverPicture: String? = null

    private val detailPicture: List<String>? = null
    private val introduce: String? = null
}