package com.imhui.security.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Entity
@Table(name = "s_user")
public class User extends BaseEntity implements Serializable {

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "username")
    private String userName;

    @Email
    @Column(name = "email")
    private String email;

    private String phone;

    @Column(name = "pwd", nullable = false)
    private String password;

    @Column(name = "salt_key")
    private String salt;

    @Column(name = "nickname")
    private String nickName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

}
