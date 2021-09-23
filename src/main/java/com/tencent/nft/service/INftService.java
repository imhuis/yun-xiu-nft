package com.tencent.nft.service;

import com.tencent.nft.entity.nft.SuperNFT;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/24
 * @description:
 */
public interface INftService {

    List<SuperNFT> getMarketList(Integer status);
}
