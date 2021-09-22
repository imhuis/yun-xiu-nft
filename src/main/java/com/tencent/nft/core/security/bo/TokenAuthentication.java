package com.tencent.nft.core.security.bo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
public class TokenAuthentication implements Authentication {

    private String token;

    public TokenAuthentication(String token) {
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
