package com.tencent.nft.mapper;

import com.tencent.nft.entity.nft.NFTProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * @author: imhuis
 * @date: 2021/9/23
 * @description:
 */
@Mapper
public interface NftProductMapper {

    Optional<NFTProduct> selectByNftId(String nftId);

    int insertNftProduct(NFTProduct nftProduct);

    int deleteByNftId(String nftId);

    /**
     * 根据userid查询出子nftid
     * @param userId
     * @return
     */
    List<Long> selectSubNftIdByUserId(String userId);
}
