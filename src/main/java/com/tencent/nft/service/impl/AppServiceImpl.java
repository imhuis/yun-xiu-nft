package com.tencent.nft.service.impl;

import com.google.common.collect.Lists;
import com.tencent.nft.core.security.SecurityUtils;
import com.tencent.nft.entity.app.vo.CollectionVO;
import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.mapper.NftProductMapper;
import com.tencent.nft.mapper.WxUserMapper;
import com.tencent.nft.service.IAppAuthService;
import com.tencent.nft.service.IAppService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/23
 * @description:
 */
@Service
public class AppServiceImpl implements IAppService {

    @Resource
    private WxUserMapper wxUserMapper;

    @Resource
    private NftProductMapper productMapper;

    @Override
    public List<CollectionVO> myLibrary() {

        String p = SecurityUtils.getCurrentUsername().get();
        List<CollectionVO> collectionVOList = Lists.newLinkedList();
        List<Long> subNftIdList = productMapper.selectSubNftIdByUserId(p);
        subNftIdList.stream().forEach(s -> {
            CollectionVO cv = new CollectionVO();
            collectionVOList.add(cv);
        });
        return Lists.newArrayList();
    }
}
