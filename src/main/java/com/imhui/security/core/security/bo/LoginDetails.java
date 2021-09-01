package com.imhui.security.core.security.bo;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
public class LoginDetails implements Serializable {

    private String username;
    private String phone;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
