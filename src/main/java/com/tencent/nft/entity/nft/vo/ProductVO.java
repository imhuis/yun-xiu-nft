package com.tencent.nft.entity.nft.vo;

import java.io.Serializable;

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

    public String getChainAddress() {
        return chainAddress;
    }

    public void setChainAddress(String chainAddress) {
        this.chainAddress = chainAddress;
    }
}
