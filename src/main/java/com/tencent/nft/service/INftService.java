package com.tencent.nft.service;

import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.SuperNFT;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/24
 * @description:
 */
public interface INftService {

    /**
     * 市场展示还需要封面
     * @param status
     * @return
     */
    List<NFTInfo> getMarketList(Integer status);
}
