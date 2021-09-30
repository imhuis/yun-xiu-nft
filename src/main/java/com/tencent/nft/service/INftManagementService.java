package com.tencent.nft.service;

import com.tencent.nft.common.base.PageBean;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NftCreateDTO;
import com.tencent.nft.entity.nft.dto.NftListQueryDTO;
import com.tencent.nft.entity.nft.dto.PreSaleDTO;
import com.tencent.nft.entity.nft.dto.SubNftQueryDTO;
import com.tencent.nft.entity.nft.vo.NftListVO;
import com.tencent.nft.entity.nft.vo.SubNftListVO;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
public interface INftManagementService {

    NFTInfo createNFT(NftCreateDTO dto);

    void deleteNft(String nftId);

    PageBean<List<NftListVO>> listNFT(Integer page, Integer size, Integer nftStatus, NftListQueryDTO nftListQueryDTO);

    PageBean<List<SubNftListVO>> listSubNFT(Integer page, Integer size, String parentNftId, SubNftQueryDTO subNFTQueryDTO);

    SuperNFT nftDetail(String nftId);

    List<String> getPosterPic(String nftId);

    void setupPreSale(PreSaleDTO n);

    void offShelf(String nftId);
}
