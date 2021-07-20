package com.imhui.security.common.base;

import com.imhui.security.common.enums.ResponseCodeEnum;
import org.springframework.http.HttpStatus;

/**
 * @author: zyixh
 * @date: 2020/1/28
 * @description:
 */
public class ResponseUtil {

    /**
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(){
        return build(ResponseCodeEnum.SUCCESS);
    }

    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(T data){
        return build(ResponseCodeEnum.SUCCESS, data);
    }

    public static ResponseResult fail(ResponseCodeEnum responseCodeEnum){
        return build(responseCodeEnum).setSuccess(false);
    }

    /**
     * 失败
     * @param responseCodeEnum
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> fail(ResponseCodeEnum responseCodeEnum, T data){
        return build(responseCodeEnum, data).setSuccess(false);
    }

    public static ResponseResult define(Integer code, String message){
        return build(code, message);
    }

    /**
     * 自定义异常码
     * @param code
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> define(Integer code, String message, T data){
        return build(code, message, data);
    }

    /**
     *
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    private static <T> ResponseResult<T> build(Integer code, String message){
        ResponseResult<T> result = new ResponseResult(code, message);
        return result;
    }

    /**
     *
     * @param code
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    private static <T> ResponseResult<T> build(Integer code, String message, T data){
        ResponseResult<T> result = new ResponseResult(code, message, data);
        return result;
    }

    /**
     *
     * @param httpStatus
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> build(HttpStatus httpStatus){
        ResponseResult<T> result = new ResponseResult(httpStatus);
        return result;
    }

    /**
     *
     * @param codeEnum
     * @param <T>
     * @return
     */
    private static <T> ResponseResult<T> build(ResponseCodeEnum codeEnum){
        ResponseResult<T> result = new ResponseResult(codeEnum);
        return result;
    }

    private static <T> ResponseResult<T> build(ResponseCodeEnum codeEnum, T data){
        ResponseResult<T> result = new ResponseResult(codeEnum, data);
        return result;
    }
}
