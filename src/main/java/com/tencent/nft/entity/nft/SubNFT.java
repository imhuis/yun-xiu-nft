package com.tencent.nft.entity.nft;

import com.tencent.nft.common.enums.NFTSaleStatusEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: imhuis
 * @date: 2021/10/11
 * @description: 子NFT
 */
public class SubNFT extends SuperNFT implements Serializable {

    // 对于子nft，nft id是当前nft编号
    private String nftId;

    private String superNFTId;

    // NFT出售状态状态 (子属性)
    private NFTSaleStatusEnum saleStatus;

    // 区块链地址 var chainAddress: String? = null

    // 售出时间
    private LocalDateTime soldTime;

    // 子NFT创建时间
//    private LocalDateTime nftCreateTime;


    @Override
    public String getNftId() {
        return nftId;
    }

    @Override
    public void setNftId(String nftId) {
        this.nftId = nftId;
    }

    public String getSuperNFTId() {
        return superNFTId;
    }

    public void setSuperNFTId(String superNFTId) {
        this.superNFTId = superNFTId;
    }

    public NFTSaleStatusEnum getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(NFTSaleStatusEnum saleStatus) {
        this.saleStatus = saleStatus;
    }

    public LocalDateTime getSoldTime() {
        return soldTime;
    }

    public void setSoldTime(LocalDateTime soldTime) {
        this.soldTime = soldTime;
    }
}
