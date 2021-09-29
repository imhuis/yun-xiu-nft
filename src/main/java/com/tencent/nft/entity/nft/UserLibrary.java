package com.tencent.nft.entity.nft;

import com.tencent.nft.entity.BaseEntity;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/9/29
 * @description:
 */
public class UserLibrary extends BaseEntity implements Serializable {

    private String userId;
    private String openId;
    private String phone;
    private String nftId;
    private String tradeNo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getNftId() {
        return nftId;
    }

    public void setNftId(String nftId) {
        this.nftId = nftId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
