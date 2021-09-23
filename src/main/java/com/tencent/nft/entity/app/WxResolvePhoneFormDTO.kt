/*
 * Copyright © 1998 - 2021 Tencent. All Rights Reserved
 * www.tencent.com
 * All rights reserved.
 */
package com.tencent.nft.entity.app;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author :imhui
 * @date :2021-04-08 12:33
 * @description :FormDTO都是接收前端传参的
 */
public class WxResolvePhoneFormDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "sessionKey参数不为空")
    private String sessionKey;

    @NotNull(message = "encryptedData参数不为空")
    private String encryptedData;

    @NotNull(message = "iv参数不为空")
    private String iv;

    public WxResolvePhoneFormDTO(){}

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
