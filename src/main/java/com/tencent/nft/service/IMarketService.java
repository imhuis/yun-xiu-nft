package com.tencent.nft.service;

import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.vo.ProductDetailVO;
import com.tencent.nft.entity.nft.vo.ProductVO;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/24
 * @description:
 */
public interface IMarketService {

    /**
     * 市场展示还需要封面
     * @param status
     * @return
     */
    List<NFTInfo> getMarketList(Integer status);

    ProductVO getProductInfo(String nftId);

    ProductDetailVO getProductDetail(String nftId);

    /**
     * 获取预约数量
     * @param nftId
     */
    long getProductReservations(String nftId);

    Boolean reserveProduct(String nftId);
}
