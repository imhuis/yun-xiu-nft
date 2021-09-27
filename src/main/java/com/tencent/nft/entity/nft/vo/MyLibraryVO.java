package com.tencent.nft.entity.nft.vo;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/9/27
 * @description:
 */
public class MyLibraryVO implements Serializable {


    private String nftId;
    private String nftName;
    // 封面图片

    private String coverPicture;
    private String owner;

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

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
