package com.tencent.nft.service;

import com.tencent.nft.entity.security.WxUser;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface UserManagerService {

     WxUser selectById(Integer id);

     List<WxUser> selectAllUser(Integer page, Integer size, Date createDate, String phone);
}
