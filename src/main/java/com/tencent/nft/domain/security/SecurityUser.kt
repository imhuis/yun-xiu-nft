package com.tencent.nft.domain.security

import org.springframework.security.core.GrantedAuthority
import java.io.Serializable

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
class SecurityUser : Serializable {

    var userId: String? = null
    var username: String? = null
    var phone: String? = null
    var email: String? = null

    // 权限
    var authorities: Set<GrantedAuthority>? = null
    var sessionId: String? = null
    var nickname: String? = null
}