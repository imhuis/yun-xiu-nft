package com.imhui.security.common.enums;

/**
 * @author: zyixh
 * @date: 2020/1/28
 * @description:
 */
public enum ResponseCodeEnum {

    SUCCESS(0, "success"),

    METHOD_NOT_ALLOWED(405, "spring.exception.405"),
    PARAMETER_MISSING(1001, "spring.exception.1001"),
    REQUEST_BODY_MISSING(1002, "spring.exception.1002");

    private final Integer code;
    private final String message;

    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return code;
    }

    public String message() {
        return message;
    }
}
