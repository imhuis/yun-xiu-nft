package com.tencent.nft.mapper;

import com.github.pagehelper.PageRowBounds;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NFTListQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description: https://www.cnblogs.com/cjsblog/p/9372258.html
 */
@Mapper
public interface NftMapper {

    /**
     * PageRowBounds rowBounds
     * @param nftListQueryDTO
     * @return
     */
    List<SuperNFT> selectSuperNFTList(@Param("nftListQueryDTO") NFTListQueryDTO nftListQueryDTO);

    int insertSuperNFT(SuperNFT superNFT);
}
