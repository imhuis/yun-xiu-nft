package com.tencent.nft.core.security.bo

import java.io.Serializable

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
class TokenInfo : Serializable {

    var token: String? = null

    // 有效时间
    var expires: Long = 0

    constructor(token: String?, expires: Long) {
        this.token = token
        this.expires = expires
    }
}