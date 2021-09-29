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

    int insert(UserLibrary userLibrary);

    List<Long> selectNftIdByPhone(String phone);

}
