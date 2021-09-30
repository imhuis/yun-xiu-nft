package com.tencent.nft.mapper.pay;

import com.tencent.nft.entity.nft.NFTProduct;
import com.tencent.nft.entity.pay.ProductStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * @author: imhuis
 * @date: 2021/9/23
 * @description:
 */
@Mapper
public interface ProductMapper {

    Optional<NFTProduct> selectByNftId(String nftId);

    int insertNftProduct(NFTProduct nftProduct);

    int deleteByNftId(String nftId);

    int updateByNftId(NFTProduct nftProduct);

    int updateStock(@Param("productId") String productId, @Param("stock") int stock);

    boolean updateStocks(@Param("productId") String productId, @Param("stock") int stock);

    Integer selectStock(String productId);

    Integer insertStock(String productId, int stock);

    void minusStockByProductId(String productId);
}
