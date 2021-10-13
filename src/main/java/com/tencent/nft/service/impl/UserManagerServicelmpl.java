package com.tencent.nft.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.mapper.WxUserMapper;
import com.tencent.nft.service.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class UserManagerServicelmpl implements UserManagerService {
    @Autowired
    private WxUserMapper wxUserMapper;


    @Override
    public WxUser selectById(Integer id) {
        return wxUserMapper.selectById(id);
    }

    @Override
    public List<WxUser> selectAllUser(Integer page, Integer size, Date create_date, String phone) {

        PageHelper.startPage(page, size);
        List<WxUser> wxUserList = wxUserMapper.selectAllUser(phone, create_date);
        return wxUserList;
    }
}
