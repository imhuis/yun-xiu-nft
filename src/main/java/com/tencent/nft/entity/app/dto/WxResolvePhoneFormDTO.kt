/*
 * Copyright © 1998 - 2021 Tencent. All Rights Reserved
 * www.tencent.com
 * All rights reserved.
 */
package com.tencent.nft.entity.app.dto

import java.io.Serializable
import javax.validation.constraints.NotNull

/**
 * @author :imhui
 * @date :2021-04-08 12:33
 * @description :FormDTO都是接收前端传参的
 */
class WxResolvePhoneFormDTO : Serializable {

    var sessionKey: @NotNull(message = "sessionKey参数不为空") String? = null
    var encryptedData: @NotNull(message = "encryptedData参数不为空") String? = null
    var iv: @NotNull(message = "iv参数不为空") String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}