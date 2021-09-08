package com.tencent.nft.service;

import com.tencent.nft.common.base.PageBean;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NFTListQueryDTO;
import com.tencent.nft.entity.nft.vo.NFTListVO;

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

    int deleteNft(String nftId);

    PageBean<List<SuperNFT>> listNFT(Integer page, Integer size, Integer nftStatus, NFTListQueryDTO nftListQueryDTO);
}
