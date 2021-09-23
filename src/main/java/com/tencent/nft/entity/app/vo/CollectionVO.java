package com.tencent.nft.entity.app.vo;

import com.tencent.nft.entity.nft.SuperNFT;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/9/24
 * @description:
 */
public class CollectionVO implements Serializable {

    private String nftName;
    private String nftFile;
    private String issuer;
    private double price;
    private String blockChainAddress;

    public String getNftName() {
        return nftName;
    }

    public void setNftName(String nftName) {
        this.nftName = nftName;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBlockChainAddress() {
        return blockChainAddress;
    }

    public void setBlockChainAddress(String blockChainAddress) {
        this.blockChainAddress = blockChainAddress;
    }
}
