package com.tencent.nft.service;

import com.tencent.nft.domain.nft.NFTInfo;
import com.tencent.nft.domain.nft.SuperNFT;

import java.util.List;

public interface NFTManagementService {

    /**
     * 新建NFT
     * @param superNFT
     * @return
     */
    int createNFT(SuperNFT superNFT);

    List<NFTInfo> listNFT();


}
