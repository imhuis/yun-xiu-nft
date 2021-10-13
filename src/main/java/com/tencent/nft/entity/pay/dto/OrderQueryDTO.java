package com.tencent.nft.entity.pay.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author: imhuis
 * @date: 2021/10/13
 * @description:
 */
public class OrderQueryDTO implements Serializable {

    private String nftId;
    private String nftName;
    private String phone;
    private LocalDate date;

    public String getNftId() {
        return nftId;
    }

    public void setNftId(String nftId) {
        this.nftId = nftId;
    }

    public String getNftName() {
        return nftName;
    }

    public void setNftName(String nftName) {
        this.nftName = nftName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
