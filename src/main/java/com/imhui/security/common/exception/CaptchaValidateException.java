package com.imhui.security.common.exception;

import org.springframework.security.core.AuthenticationException;

public class CaptchaValidateException extends AuthenticationException {

    public CaptchaValidateException(String message) {
        super(message);
    }
}
