package com.tencent.nft.mapper;

import com.tencent.nft.entity.security.WxUser;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description:
 */
@Mapper
public interface WxUserMapper {

    Optional<WxUser> selectFullByPhone(String phone);

    Optional<WxUser> selectFullByOpenId(String openId);

    Optional<WxUser> selectByPhone(String phone);

    int insert(WxUser wxUser);

    int updateByOpenid(WxUser wxUser);

    WxUser selectById(Integer id);

//    List<WxUser> selectAllUser(WxUser wxUser);

    List<WxUser> selectAllUser(String phone, String create_date);
}
