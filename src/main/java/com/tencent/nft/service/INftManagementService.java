package com.tencent.nft.service;

import com.tencent.nft.common.base.PageBean;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.NFTProduct;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NftListQueryDTO;
import com.tencent.nft.entity.nft.dto.PreSaleDTO;
import com.tencent.nft.entity.nft.dto.SubNFTQueryDTO;
import com.tencent.nft.entity.nft.vo.NFTListVO;
import com.tencent.nft.entity.nft.vo.SubNFTListVO;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
public interface INftManagementService {

    int createNFT(NFTInfo dto);

    void deleteNft(String nftId);

    PageBean<List<NFTListVO>> listNFT(Integer page, Integer size, Integer nftStatus, NftListQueryDTO nftListQueryDTO);

    PageBean<List<SubNFTListVO>> listSubNFT(Integer page, Integer size, String parentNftId, SubNFTQueryDTO subNFTQueryDTO);

    SuperNFT nftDetail(String nftId);

    List<String> getPosterPic(String nftId);

    void setPreSale(PreSaleDTO n);
}
