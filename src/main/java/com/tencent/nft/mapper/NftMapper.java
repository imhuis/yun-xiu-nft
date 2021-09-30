package com.tencent.nft.mapper;

import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.SubNFT;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NftListQueryDTO;
import com.tencent.nft.entity.nft.dto.SubNftQueryDTO;
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
     * 多条件查询父nft
     * @param nftListQueryDTO
     * @return
     */
    List<SuperNFT> selectSuperNFTList(@Param("nftListQueryDTO") NftListQueryDTO nftListQueryDTO);

    int insertSuperNFT(SuperNFT superNFT);

    int updateSuperNFT(SuperNFT superNFT);

    int deleteSuperNFT(String nftId);



    Optional<NFTInfo> selectNftInfoByNftId(String nftId);

    int insertNftInfo(NFTInfo nftInfo);

    int deleteNftINfo(String nftId);


    /**
     * 多条件查询子nft
     * @param parentNftId
     * @param subNFTQueryDTO
     * @return
     */
    List<SubNFT> selectSubNftList(@Param("superId") String parentNftId, @Param("sqd") SubNftQueryDTO subNFTQueryDTO);

    int insertSubNft(SubNFT subNFT);

    int updateSubNft(SubNFT subNFT);

    SubNFT selectSubNftByNftId(String subId);

    SubNFT selectSubNftById(Long id);

    SubNFT selectSubBftByStatus(String superNftId);
}
