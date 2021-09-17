package com.tencent.nft.mapper;

import com.tencent.nft.entity.security.WxUser;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description:
 */
public interface WxUserMapper {

    WxUser selectByPhone(String phone);

    void insert(WxUser wxUser);
}
