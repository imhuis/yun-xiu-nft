package com.tencent.nft.service;

import com.tencent.nft.common.base.PageBean;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NftListQueryDTO;
import com.tencent.nft.entity.nft.dto.SubNFTQueryDTO;
import com.tencent.nft.entity.nft.vo.NFTDetailsVO;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
public interface INftManagementService {

    /**
     * 新建NFT
     * @param superNFT
     * @return
     */
    int createNFT(SuperNFT superNFT);

    void deleteNft(String nftId);

    PageBean<List<SuperNFT>> listNFT(Integer page, Integer size, Integer nftStatus, NftListQueryDTO nftListQueryDTO);

    PageBean listSubNFT(Integer page, Integer size, String parentNftId, SubNFTQueryDTO subNFTQueryDTO);

    SuperNFT nftDetail(String nftId);
}