package com.tencent.nft.mapper;

import com.tencent.nft.entity.nft.NFTProduct;
import com.tencent.nft.entity.pay.ProductStock;
import org.apache.ibatis.annotations.Mapper;

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

    int updateByNftId(NFTProduct nftProduct);

    int updateStockByProductId(ProductStock productStock);
}
