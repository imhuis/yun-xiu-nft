package com.tencent.nft.entity.security

import com.tencent.nft.entity.BaseEntity
import java.io.Serializable
import java.time.LocalDateTime

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description: 微信用户表 s_wx_user
 */
class WxUser() : BaseEntity(), Serializable {

    // 用户ID，对应用户表 userId
    var userId: String? = null

    var otpSecret: String? = null

    // 小程序用户唯一ID
    var openId: String? = null
    var phone: String? = null
    var nickname: String? = null

    // 性别（1-男 2-女）
    var gender = 1

    // 市/省/国家
    var city: String? = null
    var province: String? = null
    var country: String? = null

    // 头像
    var avatarUrl: String? = null
    var lastLoginTime: LocalDateTime? = null

    constructor(openId: String, phone: String, nickname: String, gender: Int, city: String, province: String, country: String, avatarUrl: String) : this() {
        this.openId = openId
        this.phone = phone
        this.nickname = nickname
        this.gender = gender
        this.city = city
        this.province = province
        this.country = country
        this.avatarUrl = avatarUrl
    }
}