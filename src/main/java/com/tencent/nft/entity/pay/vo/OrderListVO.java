package com.tencent.nft.entity.pay.vo;

import com.tencent.nft.entity.pay.Order;

import java.time.LocalDateTime;

/**
 * @author: imhuis
 * @date: 2021/10/8
 * @description:
 */
public class OrderListVO extends Order {

    private String nftName;
    private int nftType;
    private String nickname;
    private LocalDateTime soldTime;

    public String getNftName() {
        return nftName;
    }

    public void setNftName(String nftName) {
        this.nftName = nftName;
    }

    public int getNftType() {
        return nftType;
    }

    public void setNftType(int nftType) {
        this.nftType = nftType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDateTime getSoldTime() {
        return soldTime;
    }

    public void setSoldTime(LocalDateTime soldTime) {
        this.soldTime = soldTime;
    }
}
