package com.tencent.nft.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.tencent.nft.common.base.PageBean;
import com.tencent.nft.common.enums.ICommonEnum;
import com.tencent.nft.common.enums.NFTSaleStatusEnum;
import com.tencent.nft.common.enums.NFTStatusEnum;
import com.tencent.nft.common.enums.NFTTypeEnum;
import com.tencent.nft.common.exception.RecordNotFoundException;
import com.tencent.nft.common.util.BusinessIdGenerate;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.NFTProduct;
import com.tencent.nft.entity.nft.SubNFT;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NftCreateDTO;
import com.tencent.nft.entity.nft.dto.NftListQueryDTO;
import com.tencent.nft.entity.nft.dto.PreSaleDTO;
import com.tencent.nft.entity.nft.dto.SubNFTQueryDTO;
import com.tencent.nft.entity.nft.vo.NFTDetailsVO;
import com.tencent.nft.entity.nft.vo.NFTListVO;
import com.tencent.nft.entity.nft.vo.SubNFTListVO;
import com.tencent.nft.mapper.NftMapper;
import com.tencent.nft.mapper.NftProductMapper;
import com.tencent.nft.service.INftManagementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
@Service
public class NftManagementServiceImpl implements INftManagementService {

    @Resource
    private NftMapper nftMapper;

    @Resource
    private NftProductMapper productMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    final String DEFAULT_ISSUER  = "安凰领御";


    @Transactional
    @Override
    public NFTInfo createNFT(NftCreateDTO dto) {
        // 生成nft id
        NFTInfo nftInfo = new NFTInfo();
        nftInfo.setNftName(dto.getNftName());
        nftInfo.setNftType(ICommonEnum.getEnum(dto.getNftType(), NFTTypeEnum.class));
        // 创建nft 状态只为待发行
        nftInfo.setNftStatus(NFTStatusEnum.WAITING);
        nftInfo.setNftFile(dto.getNftFile());
        nftInfo.setBrandOwner(dto.getBrandOwner());
        nftInfo.setIssuer(DEFAULT_ISSUER);
        nftInfo.setIntroduce(dto.getIntroduce());
        nftInfo.setNftCreateTime(LocalDateTime.now());

        nftInfo.setNftId(BusinessIdGenerate.generateSuperNftId());

        nftInfo.setCoverPicture(dto.getCoverPicture());
        nftInfo.setDetailPicture(dto.getDetailPicture().stream().collect(Collectors.joining(",")));

        // 事务执行放在一起
        nftMapper.insertSuperNFT(nftInfo);
        nftMapper.insertNftInfo(nftInfo);

        // 调用上链接口 返回nft在区块链中的地址



        return nftInfo;
    }

    @Transactional
    @Override
    public void deleteNft(String nftId) {
        // 检查状态 只有待发行中的才可以删除
        Optional<SuperNFT> superNFTOptional = nftMapper.selectSuperNFTByNftId(nftId);
        superNFTOptional.ifPresent(superNFT -> {
            // 记录存在查询状态，只有待发行的可以删除
            if (superNFT.getNftStatus() == NFTStatusEnum.WAITING){
                nftMapper.deleteSuperNFT(nftId);
            }
            nftMapper.deleteNftINfo(nftId);
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
            // 如果不是待发行，则显示金额和发行量
            if (c.getNftStatus() != NFTStatusEnum.WAITING){
                NFTProduct nftProduct = productMapper.selectByNftId(c.getNftId()).get();
                tmp.setUnitPrice(nftProduct.getUnitPrice().doubleValue());
                tmp.setCirculation(nftProduct.getCirculation());
            }else {
                // others
            }

            // 如果为发行状态为发现中或者是售罄 查询销售总量和金额
            if (c.getNftStatus() == NFTStatusEnum.UP || c.getNftStatus() == NFTStatusEnum.STOCK_OUT){
                tmp.setTotalAmount(1000);
                tmp.setTotalSales(2000L);
            }
            nftListVOList.add(tmp);
        });

        PageBean<List<NFTListVO>> pageBean = new PageBean<>(new PageInfo(superNFTList));
//        pageBean.setPages(pageInfo.getPages());
//        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setData(nftListVOList);

        return pageBean;
    }

    @Override
    public PageBean<List<SubNFTListVO>> listSubNFT(Integer page, Integer size, String parentNftId, SubNFTQueryDTO subNFTQueryDTO) {
        if (subNFTQueryDTO == null){
            subNFTQueryDTO = new SubNFTQueryDTO();
        }
        // 找不到父nft
        Optional<SuperNFT> superNFTOptional = nftMapper.selectSuperNFTByNftId(parentNftId);
        if (superNFTOptional.isEmpty()){
            throw new RecordNotFoundException();
        }
        SuperNFT superNFT = superNFTOptional.get();
        String nftId = superNFT.getNftId();
        String nftName = superNFT.getNftName();
        NFTTypeEnum fileType = superNFT.getNftType();
        String issuer = superNFT.getIssuer();
        String brandOwner = superNFT.getBrandOwner();
        NFTStatusEnum status = superNFT.getNftStatus();

        PageHelper.startPage(page, size);
        List<SubNFT> subNFTList = nftMapper.selectSubNftList(parentNftId, subNFTQueryDTO);
        List<SubNFTListVO> subNFTListVOList = Lists.newArrayList();

        subNFTList.stream().forEach(subNFT -> {
            SubNFTListVO tmp = new SubNFTListVO();
            BeanUtils.copyProperties(subNFT, tmp);
            tmp.setNftName(nftName);
            tmp.setNftType(fileType);
            tmp.setIssuer(issuer);
            tmp.setBrandOwner(brandOwner);
            if (status != NFTStatusEnum.WAITING){
                NFTProduct productInfo = productMapper.selectByNftId(nftId).get();
                tmp.setReserveStartTime(productInfo.getReserveStartTime());
                tmp.setSellStartTime(productInfo.getSellStartTime());
                tmp.setUnitPrice(productInfo.getUnitPrice().doubleValue());
            }

            subNFTListVOList.add(tmp);
        });

        PageBean<List<SubNFTListVO>> pageBean = new PageBean<>(new PageInfo(subNFTList));
        pageBean.setData(subNFTListVOList);
        return pageBean;
    }

    @Override
    public SuperNFT nftDetail(String n) {
        String nftId = StringUtils.trim(n.toLowerCase());
        Optional<SuperNFT> superNFTOptional = nftMapper.selectSuperNFTByNftId(nftId);
        // 查询不到当前nft
        if (superNFTOptional.isEmpty()){
            throw new RecordNotFoundException();
        }
        SuperNFT superNFT = superNFTOptional.get();
        NFTInfo nftInfo = nftMapper.selectNftInfoByNftId(nftId).orElse(new NFTInfo());
        NFTDetailsVO nftDetailsVO = new NFTDetailsVO();

        NFTStatusEnum status = superNFT.getNftStatus();
        if (status != NFTStatusEnum.WAITING){
            // 待发行的nft可以查询到信息
            BeanUtils.copyProperties(superNFT, nftInfo);

            Optional<NFTProduct> nftProductOptional = productMapper.selectByNftId(nftId);
            nftProductOptional.ifPresent(nftProduct -> BeanUtils.copyProperties(nftProduct, nftDetailsVO));
            BeanUtils.copyProperties(nftInfo, nftDetailsVO);

            // 预约人数
            if (status == NFTStatusEnum.APPOINTMENT){
                nftDetailsVO.setReservation(redisTemplate.opsForSet().size("yy:" + nftId));
            }

            if (status == NFTStatusEnum.UP || status == NFTStatusEnum.STOCK_OUT){
                // 计算金额
                nftDetailsVO.setTotalAmount(200);
                nftDetailsVO.setTotalSales(2300L);
            }

            return nftDetailsVO;
        }
        return superNFTOptional.get();
    }

    @Override
    public List<String> getPosterPic(String nftId) {
        Optional<NFTInfo> nftInfoOptional = nftMapper.selectNftInfoByNftId(nftId);
        if (nftInfoOptional.isPresent()){
            return Arrays.asList(nftInfoOptional.get().getDetailPicture().split(",")).stream().collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Transactional
    @Override
    public void setupPreSale(PreSaleDTO n) {
        // 查询父nft
        Optional<SuperNFT> superNFTOptional = nftMapper.selectSuperNFTByNftId(n.getNftId());
        SuperNFT superNFTInfo;
        NFTProduct nftProduct = new NFTProduct();
        if (superNFTOptional.isEmpty()){
            // 未找到这条记录
            throw new RecordNotFoundException();
            // 重复预售


        }else {
            superNFTInfo = superNFTOptional.get();
            BeanUtils.copyProperties(n, nftProduct);
            nftProduct.setNftName(superNFTInfo.getNftName());
            nftProduct.setNftStatus(NFTStatusEnum.APPOINTMENT);
        }

        // 预售创建nft详情
        productMapper.insertNftProduct(nftProduct);
        // 更新 父nft状态
        updateNftStatus(superNFTInfo.getNftId(), NFTStatusEnum.APPOINTMENT);
        // 创建子nft任务
        generateSublist(superNFTInfo.getNftId(), n.getCirculation());
        // 更新缓存


    }

    /**
     * 下架操作
     * @param nftId
     */
    @Transactional
    @Override
    public void offShelf(String nftId) {
        NFTProduct edo = new NFTProduct();
        edo.setNftId(nftId);
        edo.setNftStatus(NFTStatusEnum.OFFLINE);
        productMapper.updateByNftId(edo);

        updateNftStatus(nftId, NFTStatusEnum.OFFLINE);
    }

    @Transactional
    @Async
    void updateNftStatus(String nftId, NFTStatusEnum status) {
        // 更新 t_super_nft状态
        SubNFT edo = new SubNFT();
        edo.setNftId(nftId);
        edo.setNftStatus(status);
        nftMapper.updateSuperNFT(edo);
    }

    @Transactional
    @Async
    public void generateSublist(String nftId, Integer number){
        AtomicInteger atomicInteger = new AtomicInteger(1);

        for (int i = 0; i < number; i++){
            SubNFT subNFT = new SubNFT();
            int v = atomicInteger.getAndIncrement();
            subNFT.setNftId(nftId+String.format("%04d", v));
            subNFT.setSuperNFTId(nftId);
            subNFT.setSaleStatus(NFTSaleStatusEnum.NotSold);
            subNFT.setNftCreateTime(LocalDateTime.now());
            subNFT.setUpdateTime(LocalDateTime.now());
            nftMapper.insertSubNft(subNFT);
            atomicInteger.incrementAndGet();
        }

    }

}
