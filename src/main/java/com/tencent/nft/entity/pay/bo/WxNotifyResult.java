package com.tencent.nft.entity.pay.bo;

/**
 * @author: imhuis
 * @date: 2021/9/27
 * @description:
 */
public class WxNotifyResult {

    // 错误码，SUCCESS为清算机构接收成功
    private String code;
    // 返回信息，如非空，为错误原因
    private String message;

    public WxNotifyResult() {
    }

    public WxNotifyResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
