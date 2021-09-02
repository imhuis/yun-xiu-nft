package com.tencent.security.common.exception;

import javax.naming.AuthenticationException;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
public class UserNotActivatedException extends AuthenticationException {

    public UserNotActivatedException(String message) {
        super(message);
    }
}
