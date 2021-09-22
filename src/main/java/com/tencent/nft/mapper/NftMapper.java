package com.tencent.nft.mapper;

import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.SubNFT;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NftListQueryDTO;
import com.tencent.nft.entity.nft.dto.SubNFTQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description: https://www.cnblogs.com/cjsblog/p/9372258.html
 */
@Mapper
public interface NftMapper {

    Optional<SuperNFT> selectSuperNFTByNftId(String nftId);

    /**
     * PageRowBounds rowBounds
     * @param nftListQueryDTO
     * @return
     */
    List<SuperNFT> selectSuperNFTList(@Param("nftListQueryDTO") NftListQueryDTO nftListQueryDTO);

    int insertSuperNFT(SuperNFT superNFT);

    int deleteSuperNFT(String nftId);



    Optional<NFTInfo> selectNftInfoByNftId(String nftId);

    void insertNftInfo(NFTInfo nftInfo);



    List<SubNFT> selectSubNFTList(@Param("superId") String parentNftId, @Param("subNFTQueryDTO") SubNFTQueryDTO subNFTQueryDTO);
}
