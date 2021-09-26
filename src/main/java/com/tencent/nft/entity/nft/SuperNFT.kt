package com.tencent.nft.entity.nft

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.tencent.nft.common.enums.NFTStatusEnum
import com.tencent.nft.common.enums.NFTTypeEnum
import com.tencent.nft.entity.BaseEntity
import java.io.Serializable
import java.time.LocalDateTime

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description: 父NFT
 */
@JsonIgnoreProperties(value = ["id", "create_time", "update_time"])
open class SuperNFT : BaseEntity(), Serializable {

    // NFT编号
    open var nftId: String? = null

    // NFT名称
    var nftName: String? = null

    // NFT状态 (父NFT状态)
    var nftStatus: NFTStatusEnum? = null

    // NFT类型（1-图片 2-视频 3-音频）
    var nftType: NFTTypeEnum? = null

    // NFT文件
    var nftFile: String? = null

    // 发行方
    var issuer: String? = null

    // 品牌方
    var brandOwner: String? = null

    // 介绍
    var introduce: String? = null

    // NFT创建时间
    var nftCreateTime: LocalDateTime? = null

    var chainAddress: String? = null

}