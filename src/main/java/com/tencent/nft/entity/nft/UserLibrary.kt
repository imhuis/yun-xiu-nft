package com.tencent.nft.entity.nft

import com.tencent.nft.entity.BaseEntity
import java.io.Serializable

/**
 * @author: imhuis
 * @date: 2021/9/29
 * @description:
 */
class UserLibrary : BaseEntity(), Serializable {

    var userId: String? = null
    var openId: String? = null
    var phone: String? = null
    var nftId: String? = null
    var tradeNo: String? = null

    override fun toString(): String {
        return "UserLibrary{" +
                "userId='" + userId + '\'' +
                ", openId='" + openId + '\'' +
                ", phone='" + phone + '\'' +
                ", nftId='" + nftId + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                '}'
    }
}