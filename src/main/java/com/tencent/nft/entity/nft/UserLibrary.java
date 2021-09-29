package com.tencent.nft.entity.nft;

/**
 * @author: imhuis
 * @date: 2021/9/29
 * @description:
 */
public class UserLibrary {

    private String userId;
    private String openId;
    private String phone;
    private Long nftId;

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

    public Long getNftId() {
        return nftId;
    }

    public void setNftId(Long nftId) {
        this.nftId = nftId;
    }
}
