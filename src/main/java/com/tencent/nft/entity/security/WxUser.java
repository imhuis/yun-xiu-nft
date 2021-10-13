package com.tencent.nft.entity.security;

import com.tencent.nft.entity.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: imhuis
 * @date: 2021/10/13
 * @description: 微信用户表 s_wx_user
 */
public class WxUser extends BaseEntity implements Serializable {

    // 用户ID，对应用户表 userId
    private String userId;

    private String otpSecret;

    // 小程序用户唯一ID
    private String openId;
    private String phone;
    private String nickname;

    // 性别（1-男 2-女）
    private int gender = 1;

    // 市/省/国家
    private String city;
    private String province;
    private String country;

    // 头像
    private String avatarUrl;
    private LocalDateTime lastLoginTime;

    public WxUser() {
    }

    public WxUser(String openId, String phone, String nickname, int gender, String city, String province, String country, String avatarUrl) {
        this.openId = openId;
        this.phone = phone;
        this.nickname = nickname;
        this.gender = gender;
        this.city = city;
        this.province = province;
        this.country = country;
        this.avatarUrl = avatarUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOtpSecret() {
        return otpSecret;
    }

    public void setOtpSecret(String otpSecret) {
        this.otpSecret = otpSecret;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
