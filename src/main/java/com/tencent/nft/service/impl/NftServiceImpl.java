package com.tencent.nft.service.impl;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Lists;
import com.tencent.nft.common.enums.NFTStatusEnum;
import com.tencent.nft.common.exception.RecordNotFoundException;
import com.tencent.nft.core.security.SecurityUtils;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.NFTProduct;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NftListQueryDTO;
import com.tencent.nft.entity.nft.vo.ProductDetailVO;
import com.tencent.nft.entity.nft.vo.ProductVO;
import com.tencent.nft.mapper.NftMapper;
import com.tencent.nft.mapper.NftProductMapper;
import com.tencent.nft.service.INftService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: imhuis
 * @date: 2021/9/24
 * @description:
 */
@Service
public class NftServiceImpl implements INftService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private NftMapper nftMapper;

    @Resource
    private NftProductMapper productMapper;

    @JsonView
    @Override
    public List<NFTInfo> getMarketList(Integer status) {
        List<SuperNFT> superNFTS = nftMapper.selectSuperNFTList(new NftListQueryDTO(null, null, null, status));

        List<NFTInfo> nftInfos = superNFTS.stream()
                .map(s -> {
                    NFTInfo nftInfo = new NFTInfo();
                    BeanUtils.copyProperties(s, nftInfo);
                    Optional<NFTInfo> nftInfoOptional = nftMapper.selectNftInfoByNftId(s.getNftId());
                    nftInfoOptional.ifPresent(n -> nftInfo.setCoverPicture(n.getCoverPicture()));
                    return nftInfo;
                })
                .collect(Collectors.toList());
        return nftInfos;
    }

    @Override
    public ProductVO getProductInfo(String nftId) {
        ProductVO productVO = new ProductVO();

        Optional<SuperNFT> superNFTInfo = nftMapper.selectSuperNFTByNftId(nftId);
        if (superNFTInfo.isEmpty()){
            throw new RecordNotFoundException("暂无这个商品信息");
        }
        BeanUtils.copyProperties(superNFTInfo.get(), productVO);
        // 设置商品状态
        NFTStatusEnum productStatus = superNFTInfo.get().getNftStatus();
        productVO.setStatus(productStatus.getCode());


        if (productStatus == NFTStatusEnum.PROCESSING || productStatus == NFTStatusEnum.RESERVEING){
            Optional<NFTProduct> nftProductOptional = productMapper.selectByNftId(nftId);
            NFTProduct nftProduct = nftProductOptional.get();
            productVO.setPrice(nftProduct.getUnitPrice().doubleValue());
            productVO.setAmount(nftProduct.getCirculation());
        }
        return productVO;
    }

    @Override
    public ProductDetailVO getProductDetail(String nftId) {

        NFTInfo nftInfo = nftMapper.selectNftInfoByNftId(nftId).get();

        ProductDetailVO productDetailVO = new ProductDetailVO();
        productDetailVO.setCoverPicture(nftInfo.getCoverPicture());
        productDetailVO.setPosts(Arrays.stream(nftInfo.getDetailPicture().split(",")).collect(Collectors.toList()));
        return productDetailVO;
    }

    @Override
    public long getReservationAmount(String nftId) {
        StringBuilder sb = new StringBuilder("yy:");
        sb.append(nftId.toLowerCase());
        String key = sb.toString();
        BoundSetOperations<String,String> bso = redisTemplate.boundSetOps(key);
        return bso.size();
    }

    @Override
    public Boolean reserveProduct(String nftId) {
        // 查询商品是否存在
        StringBuilder sb = new StringBuilder("yy:");
        sb.append(nftId.toLowerCase());
        String key = sb.toString();
        BoundSetOperations<String,String> bso = redisTemplate.boundSetOps(key);

        String phone = SecurityUtils.getCurrentUsername().get();

        if (bso.isMember(phone)){
            // 存在，不需要预约
            return false;
        }else {
            bso.add(phone);
            return true;
        }
    }

}
