package com.tencent.nft.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.tencent.nft.common.base.PageBean;
import com.tencent.nft.common.enums.NFTStatusEnum;
import com.tencent.nft.common.exception.RecordNotFoundException;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.NFTProduct;
import com.tencent.nft.entity.nft.SubNFT;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NftListQueryDTO;
import com.tencent.nft.entity.nft.dto.SubNFTQueryDTO;
import com.tencent.nft.entity.nft.vo.NFTDetailsVO;
import com.tencent.nft.entity.nft.vo.SubNFTListVO;
import com.tencent.nft.mapper.NftMapper;
import com.tencent.nft.service.INftManagementService;
import com.tencent.nft.entity.nft.vo.NFTListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
@Service
public class NftManagementServiceImpl implements INftManagementService {

    @Resource
    private NftMapper nftMapper;


    @Transactional
    @Override
    public int createNFT(NFTInfo dto) {
        nftMapper.insertSuperNFT(dto);
        nftMapper.insertNftInfo(dto);
        return 0;
    }

    @Override
    public void deleteNft(String nftId) {
        // 检查状态 只有待发行中的才可以删除
        Optional<SuperNFT> superNFTOptional = nftMapper.selectSuperNFTByNftId(nftId);
        superNFTOptional.ifPresent(superNFT -> {
            // 记录存在查询状态
            System.out.println("code " + superNFT.getNftStatus().getCode());
            if (superNFT.getNftStatus() == NFTStatusEnum.WAITING){
                nftMapper.deleteSuperNFT(nftId);
            }
        });
    }

    @Override
    public PageBean<List<NFTListVO>> listNFT(Integer page, Integer size, Integer nftStatus, NftListQueryDTO nftListQueryDTO) {
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
    public PageBean<List<SubNFTListVO>> listSubNFT(Integer page, Integer size, String parentNftId, SubNFTQueryDTO subNFTQueryDTO) {
        if (subNFTQueryDTO == null){
            subNFTQueryDTO = new SubNFTQueryDTO();
        }
        SuperNFT superNFTBaseInfo = nftMapper.selectSuperNFTByNftId(parentNftId).get();
        Optional<NFTInfo> nftInfoOptional = nftMapper.selectNftInfoByNftId(parentNftId);

        PageHelper.startPage(page, size);
        List<SubNFT> subNFTList = nftMapper.selectSubNftList(parentNftId, subNFTQueryDTO);
        List<SubNFTListVO> subNFTListVOList = Lists.newArrayList();

        subNFTList.stream().forEach(subNFT -> {
            SubNFTListVO tmp = new SubNFTListVO();
            BeanUtils.copyProperties(subNFT, tmp);
            tmp.setNftName(superNFTBaseInfo.getNftName());
            tmp.setNftType(superNFTBaseInfo.getNftType());
            tmp.setIssuer(superNFTBaseInfo.getIssuer());
            nftInfoOptional.ifPresent(nftInfo -> {
//                tmp.setUnitPrice(nftInfo.getUnitPrice());
//                tmp.setReserveStartTime(nftInfo.getReserveStartTime());
//                tmp.setSellStartTime(nftInfo.getSellStartTime());
            });
            subNFTListVOList.add(tmp);
        });

        PageInfo pageInfo = new PageInfo(subNFTList);

        PageBean<List<SubNFTListVO>> pageBean = new PageBean<>();
        pageBean.setPages(pageInfo.getPages());
        pageBean.setSize(pageInfo.getSize());
        pageBean.setData(subNFTListVOList);
        return pageBean;
    }

    @Override
    public SuperNFT nftDetail(String nftId) {
        Optional<SuperNFT> superNFTOptional = nftMapper.selectSuperNFTByNftId(nftId);
        // 查询不到当前nft
        if (superNFTOptional.isEmpty()){
            throw new RecordNotFoundException();
        }
        NFTInfo nftInfo;
        if (superNFTOptional.get().getNftStatus() != NFTStatusEnum.WAITING){
            // 待发行的nft可以查询到信息
            nftInfo = nftMapper.selectNftInfoByNftId(nftId).orElse(new NFTInfo());
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

    @Transactional
    @Override
    public void setPreSale(NFTProduct n) {
        // 查询父nft
        Optional<SuperNFT> superNFTOptional = nftMapper.selectSuperNFTByNftId(n.getNftId());
        if (superNFTOptional.isEmpty()){
            // 未找到这条记录
            throw new RecordNotFoundException();
        }
        // 预售创建nft详情
        NFTProduct nftProduct = createNftInfo(n);
        // 更新 父nft状态

        // 更新缓存


    }

    @Override
    public NFTProduct createNftInfo(NFTProduct n) {



        return n;
    }
}
