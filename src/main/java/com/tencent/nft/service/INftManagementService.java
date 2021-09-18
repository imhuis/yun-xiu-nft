package com.tencent.nft.service;

import com.tencent.nft.common.base.PageBean;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NftListQueryDTO;
import com.tencent.nft.entity.nft.dto.SubNFTQueryDTO;
import com.tencent.nft.entity.nft.vo.NFTListVO;
import com.tencent.nft.entity.nft.vo.SubNFTVO;

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

    PageBean<List<NFTListVO>> listNFT(Integer page, Integer size, Integer nftStatus, NftListQueryDTO nftListQueryDTO);

    PageBean<List<SubNFTVO>> listSubNFT(Integer page, Integer size, String parentNftId, SubNFTQueryDTO subNFTQueryDTO);

    SuperNFT nftDetail(String nftId);
}
