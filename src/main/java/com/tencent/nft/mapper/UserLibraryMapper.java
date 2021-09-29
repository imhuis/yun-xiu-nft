package com.tencent.nft.mapper;

import com.tencent.nft.entity.nft.UserLibrary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/27
 * @description:
 */
@Mapper
public interface UserLibraryMapper {

    int insertUserLibrary(UserLibrary userLibrary);

    UserLibrary selectByTradeNo(String tradeNo);

    /**
     * 查询该用户的藏品
     * @param phone
     * @return
     */
    List<String> selectNftIdByPhone(String phone);

}
