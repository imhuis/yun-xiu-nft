package com.tencent.nft.service.impl;


import com.github.pagehelper.util.StringUtil;
import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.mapper.WxUserMapper;
import com.tencent.nft.service.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public List<WxUser> selectAllUser(String create_time, String phone) throws ParseException {
        WxUser wxUser=new WxUser();
        if(!StringUtil.isEmpty(create_time)){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(create_time, fmt);
            wxUser.setCreateTime(date.atStartOfDay());
        }
        if(!StringUtil.isEmpty(phone)){
            wxUser.setPhone(phone);
        }

        return wxUserMapper.selectAllUser(wxUser);
    }
}
