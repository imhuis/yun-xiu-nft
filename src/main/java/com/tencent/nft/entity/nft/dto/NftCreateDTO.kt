package com.tencent.nft.entity.nft.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description: 新增NFT controller接收实体
 */
class NftCreateDTO : Serializable {

    // NFT名称
    @NotBlank(message = "NFT名称不能为空")
    var nftName: String? = null

    // NFT类型（1-图片 2-视频 3-音频）
    var nftType = -1

    // NFT文件
    var nftFile: String? = null

    // 发行方
    @NotBlank(message = "发行方名称不能为空")
    var issuer: String? = null

    // 介绍
    var introduce: String? = null

    // 封面图片
    var coverPicture: String? = null

    // 详细介绍，接收图片数组
    var detailPicture: List<String>? = null
}