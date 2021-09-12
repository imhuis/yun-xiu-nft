package com.tencent.nft.common.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.tencent.nft.common.enums.ResponseCodeEnum;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author: zyixh
 * @date: 2020/1/28
 * @description:
 */
public class ResponseResult<T> implements Serializable {

    private boolean success = true;

    /**
     * 业务状态码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回对象
     */
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private String timestamp;

    public ResponseResult() {
    }

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(HttpStatus httpStatus){
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
    }

    public ResponseResult(HttpStatus httpStatus, T o){
        this(httpStatus);
        this.data = o;
    }

    public ResponseResult(ResponseCodeEnum codeEnum){
        this.code = codeEnum.code();
        this.message = codeEnum.message();
    }

    public ResponseResult(ResponseCodeEnum codeEnum, T o){
        this(codeEnum);
        this.data = o;
    }

    public boolean isSuccess() {
        return success;
    }

    public ResponseResult<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTimestamp() {
        DateTime dateTime = DateTime.now().withZone(DateTimeZone.UTC);
        return dateTime.toString();
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
