package com.imhui.security.common.exception;

import javax.naming.AuthenticationException;

public class UserNotActivatedException extends AuthenticationException {

    public UserNotActivatedException(String message) {
        super(message);
    }
}
