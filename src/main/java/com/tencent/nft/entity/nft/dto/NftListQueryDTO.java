package com.tencent.nft.entity.nft.dto;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/10/11
 * @description:
 */
public class NftListQueryDTO implements Serializable {


    private String nftId;
    private String nftName;
    private Integer nftType;
    private Integer nftStatus;

    public NftListQueryDTO() {
    }

    public NftListQueryDTO(String nftId, String nftName, Integer nftType, Integer nftStatus) {
        this.nftId = nftId;
        this.nftName = nftName;
        this.nftType = nftType;
        this.nftStatus = nftStatus;
    }

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

    public Integer getNftType() {
        return nftType;
    }

    public void setNftType(Integer nftType) {
        this.nftType = nftType;
    }

    public Integer getNftStatus() {
        return nftStatus;
    }

    public void setNftStatus(Integer nftStatus) {
        this.nftStatus = nftStatus;
    }
}
