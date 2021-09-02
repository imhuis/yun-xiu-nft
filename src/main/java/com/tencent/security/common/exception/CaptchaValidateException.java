package com.tencent.security.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
public class CaptchaValidateException extends AuthenticationException {

    public CaptchaValidateException(String message) {
        super(message);
    }
}
