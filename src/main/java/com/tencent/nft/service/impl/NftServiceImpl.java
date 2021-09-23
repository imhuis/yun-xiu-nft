package com.tencent.nft.service.impl;

import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NftListQueryDTO;
import com.tencent.nft.mapper.NftMapper;
import com.tencent.nft.service.INftService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/24
 * @description:
 */
@Service
public class NftServiceImpl implements INftService {


    @Resource
    private NftMapper nftMapper;

    @Override
    public List<SuperNFT> getMarketList(Integer status) {
        return nftMapper.selectSuperNFTList(new NftListQueryDTO(null, null, null, status));
    }
}
