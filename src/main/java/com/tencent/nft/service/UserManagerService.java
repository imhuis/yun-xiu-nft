package com.tencent.nft.service;

import com.tencent.nft.entity.security.WxUser;

public interface UserManagerService {

     WxUser selectById(Integer id);
}
