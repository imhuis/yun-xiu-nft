package com.tencent.nft.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.tencent.nft.common.util.UUIDUtil;
import com.tencent.nft.common.util.WXPayUtil;
import com.tencent.nft.core.config.WxGroupConfig;
import com.tencent.nft.core.security.SecurityUtils;
import com.tencent.nft.entity.app.vo.CollectionVO;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.NFTProduct;
import com.tencent.nft.entity.nft.SubNFT;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.pay.TradeInfo;
import com.tencent.nft.entity.pay.bo.PayDetailBO;
import com.tencent.nft.entity.pay.PayRequestDTO;
import com.tencent.nft.entity.pay.bo.PrepayBO;
import com.tencent.nft.mapper.NftMapper;
import com.tencent.nft.mapper.NftProductMapper;
import com.tencent.nft.mapper.TradeMapper;
import com.tencent.nft.mapper.UserLibraryMapper;
import com.tencent.nft.service.IAppService;
import com.tencent.nft.service.handler.WechatPayHandler;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: imhuis
 * @date: 2021/9/23
 * @description:
 */
@Service
public class AppServiceImpl implements IAppService {

    @Resource
    private NftMapper nftMapper;

    @Resource
    private UserLibraryMapper userLibraryMapper;

    @Resource
    private NftProductMapper productMapper;

    @Resource
    private TradeMapper tradeMapper;

    @Autowired
    private WechatPayHandler payHandler;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private WxGroupConfig wxGroupConfig;

    @Override
    public List<NFTInfo> myLibrary() {
        String p = SecurityUtils.getCurrentUsername().get();
        List<NFTInfo> collectionVOList = Lists.newLinkedList();
        List<Long> subNftList = userLibraryMapper.selectNftIdByPhone(p);
        subNftList.stream().forEach(s -> {
            SubNFT subNFT = nftMapper.selectSubNftById(s);
            NFTInfo nftInfo = nftMapper.selectNftInfoByNftId(subNFT.getSuperNFTId()).get();
            nftInfo.setCoverPicture(nftInfo.getCoverPicture());
            nftInfo.setNftName(nftInfo.getNftName());
            collectionVOList.add(nftInfo);
        });
        return collectionVOList;
    }

    @Override
    public CollectionVO collectionDetails(String subId) {
        SubNFT zi = nftMapper.selectSubNftByNftId(subId);
        SuperNFT superNFT = nftMapper.selectSuperNFTByNftId(zi.getSuperNFTId()).get();
        NFTProduct nftProduct = productMapper.selectByNftId(zi.getSuperNFTId()).get();
        CollectionVO collectionVO = new CollectionVO();

        collectionVO.setNftName(superNFT.getNftName());
        collectionVO.setNftFile(superNFT.getNftFile());
        collectionVO.setIssuer(superNFT.getIssuer());
        collectionVO.setBrandOwner(superNFT.getBrandOwner());
        collectionVO.setPrice(nftProduct.getUnitPrice().doubleValue());
        collectionVO.setBlockChainAddress(UUIDUtil.generateUUID());
        return collectionVO;
    }

    @Override
    public PrepayBO prePay(PayRequestDTO dto) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String dateStr = dtf.format(LocalDateTime.now());
        final String tradeNo = dateStr + UUIDUtil.generateUUID();
        PayDetailBO payDetailBO = new PayDetailBO();
        payDetailBO.setTotal(1);
        payDetailBO.setDescription("数字藏品-" + dto.getNftId());
        payDetailBO.setTradeNo(tradeNo);
        payDetailBO.setOpenId(dto.getOpenId());
        String prepayId = payHandler.handler(payDetailBO);

        // 异步插入数据表
        createOrder(payDetailBO);

        // 处理下单结果内容，生成前端调起微信支付的sign
        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        final String nonceStr = RandomUtil.randomString(32);
        final String packages = "prepay_id=" + prepayId;

        Map<String, String> wxPayMap = new HashMap<>();
        wxPayMap.put("appId", wxGroupConfig.getAppletAppId());
        wxPayMap.put("timeStamp", timestamp);
        wxPayMap.put("nonceStr", nonceStr);
        wxPayMap.put("package", packages);
        wxPayMap.put("signType", "MD5");
        String sign = WXPayUtil.generateSignature(wxPayMap, wxGroupConfig.getWxPayKey());


        // 小程序调起支付API
        PrepayBO prepayBO = new PrepayBO();
        prepayBO.setAppId(wxGroupConfig.getAppId());
        prepayBO.setTimeStamp(timestamp);
        prepayBO.setNonceStr(nonceStr);
        prepayBO.setPrepayId(prepayId);
        prepayBO.setPackageStr(packages);
        prepayBO.setSignType("MD5");
        prepayBO.setPaySign(sign);
        return prepayBO;
    }


    @Async
    public void createOrder(PayDetailBO payDetailBO){
        // 默认新增状态为无效订单
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setTradeNo(payDetailBO.getTradeNo());
        // 分 >> 元
        tradeInfo.setAmount(payDetailBO.getTotal());
        tradeInfo.setDescription(payDetailBO.getDescription());
        tradeInfo.setPayer(payDetailBO.getOpenId());
        tradeMapper.insert(tradeInfo);
    }
}
