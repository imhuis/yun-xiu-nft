package com.tencent.nft.entity.nft.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: imhuis
 * @date: 2021/9/25
 * @description: 商品详情表
 */
public class ProductVO implements Serializable {

    // 编号
    private String nftId;
    // 名称
    private String nftName;
    // 状态
    private int status;
    // 资源类型
    private int nftType;
    // 文件内容
    private String nftFile;

    // 发行方
    private String issuer;
    // 品牌方
    private String brandOwner;
    // 介绍
    private String introduce;
    // 价格
    private double price;
    // 发行量
    private int amount;

    private LocalDateTime sellStartTime;

    // 区块链地址
    private String chainAddress;

    // 用户权限
    // 预约状态时：1-已经预约 2-未预约（默认）
    // 购买状态时：1-已经购买 2-未购买（默认）
    @JsonProperty("ps")
    private int personStatus = 2;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNftType() {
        return nftType;
    }

    public void setNftType(int nftType) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getSellStartTime() {
        return sellStartTime;
    }

    public void setSellStartTime(LocalDateTime sellStartTime) {
        this.sellStartTime = sellStartTime;
    }

    public String getChainAddress() {
        return chainAddress;
    }

    public void setChainAddress(String chainAddress) {
        this.chainAddress = chainAddress;
    }

    public int getPersonStatus() {
        return personStatus;
    }

    public void setPersonStatus(int personStatus) {
        this.personStatus = personStatus;
    }
}
