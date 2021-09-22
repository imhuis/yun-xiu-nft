/*
 * Copyright © 1998 - 2021 Tencent. All Rights Reserved
 * www.tencent.com
 * All rights reserved.
 */
package com.tencent.nft.entity.app

import java.io.Serializable
import javax.validation.constraints.NotNull

/**
 * @author :imhui
 * @date :2021-04-08 12:33
 * @description :FormDTO都是接收前端传参的
 */
class WxUserProfileFormDTO : Serializable {

    var openId: @NotNull(message = "openId不能为空") String? = null
    var nickName: @NotNull(message = "昵称不能为空") String? = null
    var gender: Int? = null
    var city: String? = null
    var province: String? = null
    var country: String? = null
    var avatarUrl: @NotNull(message = "头像不能为空") String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}