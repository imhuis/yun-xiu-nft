package com.tencent.nft.mapper;

import com.tencent.nft.entity.security.WxUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description:
 */
@Mapper
public interface WxUserMapper {

    WxUser selectByPhone(String phone);

    void insert(WxUser wxUser);

    WxUser selectById(Integer id);

    List<WxUser> selectAllUser(WxUser wxUser);
}
