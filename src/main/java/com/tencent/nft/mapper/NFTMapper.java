package com.tencent.nft.mapper;

import com.tencent.nft.domain.nft.SuperNFT;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NFTMapper {

    @Select("select ")
    List<SuperNFT> selectSuperNFTList();

    int insertSuperNFT(SuperNFT superNFT);
}
