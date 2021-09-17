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

    private String nickName;
    private String avatarUrl;

    @NotNull(message = "jsCode参数不为空")
    private String jsCode;

    @NotNull(message = "encryptedData参数不为空")
    private String encryptedData;

    @NotNull(message = "iv参数不为空")
    private String iv;

    public WxResolvePhoneFormDTO(){}

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public String getJsCode() {
        return jsCode;
    }

    public void setJsCode(String jsCode) {
        this.jsCode = jsCode;
    }
}
