package com.tencent.nft.entity.nft.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/26
 * @description:
 */
public class ProductDetailVO implements Serializable {

    @JsonProperty("cover")
    private String coverPicture;
    private List<String> posts;

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public List<String> getPosts() {
        return posts;
    }

    public void setPosts(List<String> posts) {
        this.posts = posts;
    }
}
