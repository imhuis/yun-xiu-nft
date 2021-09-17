package com.tencent.nft.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.tencent.nft.common.base.PageBean;
import com.tencent.nft.common.enums.NFTStatusEnum;
import com.tencent.nft.common.exception.RecordNotFoundException;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.SubNFT;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NftListQueryDTO;
import com.tencent.nft.entity.nft.dto.SubNFTQueryDTO;
import com.tencent.nft.entity.nft.vo.NFTDetailsVO;
import com.tencent.nft.entity.nft.vo.SubNFTVO;
import com.tencent.nft.mapper.NftMapper;
import com.tencent.nft.service.INftManagementService;
import com.tencent.nft.entity.nft.vo.NFTListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void deleteNft(String nftId) {
        // 检查状态 只有待发行中的才可以删除
        Optional<SuperNFT> superNFTOptional = nftMapper.selectSuperNFTByNftId(nftId);
        superNFTOptional.ifPresent(superNFT -> {
            // 记录存在查询状态
            if (superNFT.getNftStatus() == NFTStatusEnum.WAITING){
                nftMapper.deleteSuperNFT(nftId);
            }
        });
    }

    @Override
    public PageBean<List<NFTListVO>> listNFT(Integer page, Integer size, Integer nftStatus, NftListQueryDTO nftListQueryDTO) {
//        PageRowBounds rowBounds = new PageRowBounds(page, size);
        // 执行分页
        PageHelper.startPage(page, size);
        List<SuperNFT> superNFTList = nftMapper.selectSuperNFTList(nftListQueryDTO);
        List<NFTListVO> nftListVOList = Lists.newArrayList();
//        Collections.copy(superNFTList, nftListVOList);
        superNFTList.stream().forEach(c -> {
            NFTListVO tmp = new NFTListVO();
            BeanUtils.copyProperties(c, tmp);
            if (c.getNftStatus() == NFTStatusEnum.PROCESSING || c.getNftStatus() == NFTStatusEnum.SOLDOUT){
                // 如果为发行状态为发现中或者是售罄 查询销售金额
                tmp.setTotalAmount(1000);
                tmp.setTotalSales(2000L);
            }
            tmp.setUnitPrice(1);
            tmp.setCirculation(2000);
            nftListVOList.add(tmp);
        });

        PageInfo pageInfo = new PageInfo(nftListVOList);
        PageBean<List<NFTListVO>> pageBean = new PageBean<>();
        pageBean.setPages(pageInfo.getPages());
        pageBean.setSize(pageInfo.getSize());
        pageBean.setData(nftListVOList);

        return pageBean;
    }

    @Override
    public PageBean listSubNFT(Integer page, Integer size, String parentNftId, SubNFTQueryDTO subNFTQueryDTO) {
        if (subNFTQueryDTO == null){
            subNFTQueryDTO = new SubNFTQueryDTO();
        }
        Optional<SuperNFT> superNFTOptional = nftMapper.selectSuperNFTByNftId(parentNftId);
        Optional<NFTInfo> nftInfoOptional = nftMapper.selectNFTInfoByNftId(parentNftId);

        PageHelper.startPage(page, size);
        List<SubNFT> subNFTList = nftMapper.selectSubNFTList(parentNftId, subNFTQueryDTO);
        List<SubNFTVO> subNFTVOList = Lists.newArrayList();

        BeanUtils.copyProperties(subNFTList, subNFTVOList);
//        System.arraycopy(subNFTList, 0 , subNFTVOList, 0, subNFTList.size());

        subNFTVOList.stream().forEach(subNFTVO -> nftInfoOptional.ifPresent(nftInfo -> {
            SuperNFT s = superNFTOptional.get();
            subNFTVO.setNftName(s.getNftName());
            subNFTVO.setNftType(s.getNftType());
            subNFTVO.setIssuer(s.getIssuer());

            subNFTVO.setUnitPrice(nftInfo.getUnitPrice());
            subNFTVO.setReserveStartTime(nftInfo.getReserveStartTime());
            subNFTVO.setSellStartTime(nftInfo.getSellStartTime());
        }));


        PageInfo pageInfo = new PageInfo(subNFTList);

        PageBean<List<SubNFTVO>> pageBean = new PageBean<>();
        pageBean.setPages(pageInfo.getPages());
        pageBean.setSize(pageInfo.getSize());
        pageBean.setData(subNFTVOList);

        return pageBean;
    }

    @Override
    public SuperNFT nftDetail(String nftId) {
        Optional<SuperNFT> superNFTOptional  = nftMapper.selectSuperNFTByNftId(nftId);
        if (superNFTOptional.isEmpty()){
            throw new RecordNotFoundException();
        }
        NFTInfo nftInfo;
        if (superNFTOptional.get().getNftStatus() != NFTStatusEnum.WAITING){
            // 待发行的nft可以查询到信息
            nftInfo = nftMapper.selectNFTInfoByNftId(nftId).orElse(new NFTInfo());
            BeanUtils.copyProperties(superNFTOptional.get(), nftInfo);

            NFTDetailsVO nftDetailsVO = new NFTDetailsVO();
            if (nftInfo.getNftStatus() == NFTStatusEnum.PROCESSING || nftInfo.getNftStatus() == NFTStatusEnum.SOLDOUT){
                nftDetailsVO.setTotalAmount(200);
                nftDetailsVO.setTotalSales(2300L);
            }
            BeanUtils.copyProperties(nftInfo, nftDetailsVO);
            return nftDetailsVO;
        }
        return superNFTOptional.get();
    }
}
