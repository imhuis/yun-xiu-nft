package com.tencent.nft.entity.security;

import com.tencent.nft.entity.BaseEntity;

import javax.validation.constraints.Email;
import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/10/13
 * @description: 用户表 s_user
 */
public class User extends BaseEntity implements Serializable {

    private String userId;

    private String username;

    @Email
    private String email;

    private String phone;

    private String password;

    private String salt;

    private String nickname;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
