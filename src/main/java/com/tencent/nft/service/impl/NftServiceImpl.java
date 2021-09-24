package com.tencent.nft.service.impl;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Lists;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NftListQueryDTO;
import com.tencent.nft.mapper.NftMapper;
import com.tencent.nft.service.INftService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: imhuis
 * @date: 2021/9/24
 * @description:
 */
@Service
public class NftServiceImpl implements INftService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private NftMapper nftMapper;

    @JsonView
    @Override
    public List<NFTInfo> getMarketList(Integer status) {
        List<SuperNFT> superNFTS = nftMapper.selectSuperNFTList(new NftListQueryDTO(null, null, null, status));

        List<NFTInfo> nftInfos = superNFTS.stream()
                .map(s -> {
                    NFTInfo nftInfo = new NFTInfo();
                    BeanUtils.copyProperties(s, nftInfo);
                    Optional<NFTInfo> nftInfoOptional = nftMapper.selectNftInfoByNftId(s.getNftId());
                    nftInfoOptional.ifPresent(n -> nftInfo.setCoverPicture(n.getCoverPicture()));
                    return nftInfo;
                })
                .collect(Collectors.toList());
        return nftInfos;
    }

    @Override
    public NFTInfo getProductDetail(String nftId) {
        Optional<NFTInfo> nftInfoOptional = nftMapper.selectNftInfoByNftId(nftId);

        return nftInfoOptional.get();
    }

    @Override
    public long getReservationAmount(String nftId) {
        StringBuilder sb = new StringBuilder("yy:");
        sb.append(nftId.toLowerCase());
        return redisTemplate.boundListOps(sb).size();
    }

}
