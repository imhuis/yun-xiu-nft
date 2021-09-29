package com.tencent.nft.service.impl;

import com.tencent.nft.common.enums.NFTStatusEnum;
import com.tencent.nft.common.exception.business.RecordNotFoundException;
import com.tencent.nft.core.security.SecurityUtils;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.NFTProduct;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NftListQueryDTO;
import com.tencent.nft.entity.nft.vo.ProductDetailVO;
import com.tencent.nft.entity.nft.vo.ProductVO;
import com.tencent.nft.mapper.NftMapper;
import com.tencent.nft.mapper.NftProductMapper;
import com.tencent.nft.service.IMarketService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
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
public class MarketServiceImpl implements IMarketService {

    final Logger log = LoggerFactory.getLogger(MarketServiceImpl.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private NftMapper nftMapper;

    @Resource
    private NftProductMapper productMapper;

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
    public ProductVO getProductInfo(String productId) {
        ProductVO productVO = new ProductVO();

        Optional<SuperNFT> superNFTInfo = nftMapper.selectSuperNFTByNftId(productId);
        if (superNFTInfo.isEmpty()){
            throw new RecordNotFoundException("暂无这个商品信息");
        }
        BeanUtils.copyProperties(superNFTInfo.get(), productVO);
        // 设置商品状态
        SuperNFT superNFT = superNFTInfo.get();
        NFTStatusEnum productStatus = superNFT.getNftStatus();
        productVO.setStatus(superNFT.getNftStatus().getCode());
        productVO.setNftType(superNFT.getNftType().getCode());
        productVO.setChainAddress(superNFT.getChainAddress());


        if (productStatus == NFTStatusEnum.APPOINTMENT || productStatus == NFTStatusEnum.UP){
            Optional<NFTProduct> nftProductOptional = productMapper.selectByNftId(productId);
            NFTProduct nftProduct = nftProductOptional.get();
            productVO.setPrice(nftProduct.getUnitPrice().doubleValue());
            productVO.setAmount(nftProduct.getCirculation());
            productVO.setSellStartTime(nftProduct.getSellStartTime());
        }


        // 根据用户token 状态该藏品对于用户的状态
        String phone = Optional.ofNullable(SecurityUtils.getCurrentUsername().get()).orElse("");
        log.info("current user phone: {}", phone);
        // 该用户存在
        if (StringUtils.isNoneBlank(phone)){
            // 判断是否预约过
            // 1 - 已经购买
            // 2 - 未购买
            if (productStatus == NFTStatusEnum.APPOINTMENT){
                BoundSetOperations<String,String> bso = stringRedisTemplate.boundSetOps(getReserveChar(productId));
                System.out.println(bso.isMember(phone));
                if (bso.isMember(phone) == true){
                    productVO.setPersonStatus(1);
                }
            }
            // 判断是否购买过
            if (productStatus == NFTStatusEnum.UP){

            }
        }

        return productVO;
    }

    @Override
    public ProductDetailVO getProductDetail(String nftId) {

//        Optional<NFTInfo> nftInfo = nftMapper.selectNftInfoByNftId(nftId);
        NFTInfo nftInfo = Optional.ofNullable(nftMapper.selectNftInfoByNftId(nftId))
                .get()
                .orElseThrow(RecordNotFoundException::new);

        ProductDetailVO productDetailVO = new ProductDetailVO();
        productDetailVO.setCoverPicture(nftInfo.getCoverPicture());
        productDetailVO.setPosts(Arrays.stream(nftInfo.getDetailPicture().split(",")).collect(Collectors.toList()));
        return productDetailVO;
    }

    @Override
    public long getProductAmount(String productId) {
        Optional<NFTProduct> nftProductOptional = productMapper.selectByNftId(productId);
        if (nftProductOptional.isEmpty()){
            return 0;
        }
        NFTStatusEnum statusEnum = nftProductOptional.get().getNftStatus();
        if (statusEnum == NFTStatusEnum.APPOINTMENT){
            return getProductReservationAmount(productId);
        }
        if (statusEnum == NFTStatusEnum.UP){
            return getProductPurchaseAmount(productId);
        }
        return 0;
    }

    @Override
    public Boolean reserveProduct(String productId) {
        // 查询商品是否存在
        BoundSetOperations<String,String> bso = stringRedisTemplate.boundSetOps(getReserveChar(productId));

        String phone = SecurityUtils.getCurrentUsername().get();
        if (bso.isMember(phone)){
            // 存在，重复预约
            return false;
        }else {
            bso.add(phone);
            return true;
        }
    }

    @Override
    public long getProductReservationAmount(String productId) {
        BoundSetOperations<String,String> bso = stringRedisTemplate.boundSetOps(getReserveChar(productId));
        return bso.size();
    }

    @Override
    public long getProductPurchaseAmount(String productId) {
        BoundSetOperations<String,String> bso = stringRedisTemplate.boundSetOps(getReservesChar(productId));
        return bso.size();
    }

    /**
     * 获取预约键的key
     * @param productId
     * @return
     */
    protected String getReserveChar(String productId){
        StringBuilder sb = new StringBuilder("yy:");
        sb.append(productId.trim().toLowerCase());
        return sb.toString();
    }

    /**
     * 获取购买数量键的key
     * @param productId
     * @return
     */
    protected String getReservesChar(String productId){
        StringBuilder sb = new StringBuilder("yy:");
        sb.append(productId.trim().toLowerCase());
        return sb.toString();
    }

}
