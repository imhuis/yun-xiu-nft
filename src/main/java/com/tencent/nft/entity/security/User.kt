package com.tencent.nft.entity.security

import com.tencent.nft.entity.BaseEntity
import java.io.Serializable
import javax.validation.constraints.Email

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description: 用户表 s_user
 */
class User : BaseEntity(), Serializable {

    var userId: String? = null

    var username: String? = null

    var email: @Email String? = null

    var phone: String? = null

    var password: String? = null

    var salt: String? = null

    var nickname: String? = null
}