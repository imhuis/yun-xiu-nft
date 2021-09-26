package com.tencent.nft.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/27
 * @description:
 */
@Mapper
public interface UserLibraryMapper {

    List<Long> selectNftIdByPhone(String phone);

}
