package com.tencent.nft.entity.nft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/10/11
 * @description:
 */
@JsonIgnoreProperties(value = {"id", "create_time", "update_time"})
public class NFTInfo extends SuperNFT implements Serializable {

    // 封面图片
    private String coverPicture;

    // 详细介绍图片
    private String detailPicture;

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getDetailPicture() {
        return detailPicture;
    }

    public void setDetailPicture(String detailPicture) {
        this.detailPicture = detailPicture;
    }
}
