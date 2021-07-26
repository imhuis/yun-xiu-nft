package com.imhui.security.security;

import java.io.Serializable;

public class TokenInfo implements Serializable {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
