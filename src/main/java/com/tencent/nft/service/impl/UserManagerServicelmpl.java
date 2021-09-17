package com.tencent.nft.service.impl;


import com.tencent.nft.mapper.WxUserMapper;
import com.tencent.nft.service.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagerServicelmpl implements UserManagerService {
    @Autowired
    private WxUserMapper wxUserMapper;


    @Override
    public Object selectById(Integer id) {
        return wxUserMapper.selectById(id);
    }
}
