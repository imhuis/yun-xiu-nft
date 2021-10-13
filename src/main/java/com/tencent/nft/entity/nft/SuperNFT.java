package com.tencent.nft.entity.nft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tencent.nft.common.enums.NFTStatusEnum;
import com.tencent.nft.common.enums.NFTTypeEnum;
import com.tencent.nft.entity.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: imhuis
 * @date: 2021/10/11
 * @description: 父NFT
 */
@JsonIgnoreProperties(value = {"id", "create_time", "update_time"})
public class SuperNFT extends BaseEntity implements Serializable {

    // NFT编号
    private String nftId;
    // NFT名称
    private String nftName;
    // NFT状态 (父NFT状态)
    private NFTStatusEnum nftStatus;
    // NFT类型（1-图片 2-视频 3-音频）
    private NFTTypeEnum nftType;
    // NFT文件
    private String nftFile;
    // 发行方
    private String issuer;
    // 品牌方
    private String brandOwner;
    // 介绍
    private String introduce;
    // NFT创建时间
    private LocalDateTime nftCreateTime;
    // 区块链地址
    private String chainAddress;

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

    public NFTStatusEnum getNftStatus() {
        return nftStatus;
    }

    public void setNftStatus(NFTStatusEnum nftStatus) {
        this.nftStatus = nftStatus;
    }

    public NFTTypeEnum getNftType() {
        return nftType;
    }

    public void setNftType(NFTTypeEnum nftType) {
        this.nftType = nftType;
    }

    public String getNftFile() {
        return nftFile;
    }

    public void setNftFile(String nftFile) {
        this.nftFile = nftFile;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getBrandOwner() {
        return brandOwner;
    }

    public void setBrandOwner(String brandOwner) {
        this.brandOwner = brandOwner;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public LocalDateTime getNftCreateTime() {
        return nftCreateTime;
    }

    public void setNftCreateTime(LocalDateTime nftCreateTime) {
        this.nftCreateTime = nftCreateTime;
    }

    public String getChainAddress() {
        return chainAddress;
    }

    public void setChainAddress(String chainAddress) {
        this.chainAddress = chainAddress;
    }
}
