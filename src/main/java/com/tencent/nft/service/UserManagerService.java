package com.tencent.nft.service;

import com.tencent.nft.common.base.FeedBack;
import com.tencent.nft.entity.security.WxUser;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface UserManagerService {

     WxUser selectById(Integer id);

     List<WxUser> selectAllUser(Date create_date, String phone) throws ParseException;
}
