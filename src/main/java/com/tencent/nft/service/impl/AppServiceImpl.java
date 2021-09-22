package com.tencent.nft.service.impl;

import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.mapper.WxUserMapper;
import com.tencent.nft.service.IAppAuthService;
import com.tencent.nft.service.IAppService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: imhuis
 * @date: 2021/9/23
 * @description:
 */
@Service
public class AppServiceImpl implements IAppService {

    @Resource
    private WxUserMapper wxUserMapper;

}
