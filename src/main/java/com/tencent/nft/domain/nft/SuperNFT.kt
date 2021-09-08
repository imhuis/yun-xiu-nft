package com.tencent.nft.domain.nft

import com.tencent.nft.domain.BaseEntity
import com.tencent.nft.common.enums.NFTTypeEnum
import java.io.Serializable
import java.time.LocalDateTime

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description: 父NFT
 */
open class SuperNFT : BaseEntity(), Serializable {

    // NFT编号
    private val nftId: String? = null

    // NFT名称
    private val nftName: String? = null

    // NFT类型（1-图片 2-视频 3-音频）
    private val nftType: NFTTypeEnum? = null

    // NFT文件
    private val nftFile: String? = null

    // 发行方
    private val issuer: String? = null

    // 介绍
    private val introduce: String? = null

    // 封面图片
    private val coverPicture: String? = null

    // 详细介绍
    private val detailPicture: String? = null

}