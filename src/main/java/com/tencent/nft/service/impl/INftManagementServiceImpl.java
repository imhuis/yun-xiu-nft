package com.tencent.nft.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import com.google.common.collect.Lists;
import com.tencent.nft.common.base.PageBean;
import com.tencent.nft.common.enums.NFTStatusEnum;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NFTListQueryDTO;
import com.tencent.nft.mapper.NftMapper;
import com.tencent.nft.service.INftManagementService;
import com.tencent.nft.entity.nft.vo.NFTListVO;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
@Service
public class INftManagementServiceImpl implements INftManagementService {

    @Resource
    private NftMapper nftMapper;

    @Override
    @Transactional
    public int createNFT(SuperNFT superNFT) {

        nftMapper.insertSuperNFT(superNFT);
        return 0;
    }

    @Override
    public int deleteNft(String nftId) {
        return 0;
    }

    @Override
    public PageBean<List<SuperNFT>> listNFT(Integer page, Integer size, Integer nftStatus, NFTListQueryDTO nftListQueryDTO) {
//        PageRowBounds rowBounds = new PageRowBounds(page, size);
        PageHelper.startPage(page, size);
        List<SuperNFT> superNFTList = nftMapper.selectSuperNFTList(nftListQueryDTO);
        List<NFTListVO> nftListVOList = Lists.newArrayList();
        Collections.copy(superNFTList, nftListVOList);
        superNFTList.stream().forEach(c -> {
            if (c.getNftStatus() == NFTStatusEnum.PROCESSING){
                // 如果为发行状态查询销售金额
            }
        });
        PageInfo pageInfo = new PageInfo(superNFTList);
        pageInfo.getPages();

        PageBean<List<SuperNFT>> pageBean = new PageBean<>();
        pageBean.setPages(pageInfo.getPages());
        pageBean.setSize(pageInfo.getSize());
        pageBean.setData(superNFTList);

        return pageBean;
    }
}
