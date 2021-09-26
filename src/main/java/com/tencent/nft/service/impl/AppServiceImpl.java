package com.tencent.nft.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.tencent.nft.core.security.SecurityUtils;
import com.tencent.nft.entity.app.vo.CollectionVO;
import com.tencent.nft.entity.pay.PayDetailBO;
import com.tencent.nft.entity.pay.PayRequestDTO;
import com.tencent.nft.entity.pay.PrepayBO;
import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.mapper.NftProductMapper;
import com.tencent.nft.mapper.WxUserMapper;
import com.tencent.nft.service.IAppAuthService;
import com.tencent.nft.service.IAppService;
import com.tencent.nft.service.handler.WechatPayHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/23
 * @description:
 */
@Service
public class AppServiceImpl implements IAppService {

    @Resource
    private NftProductMapper productMapper;

    @Autowired
    private WechatPayHandler payHandler;

    @Autowired
    private RedisTemplate redisTemplate;

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

    @Override
    public void reserve(String nftId) {
        String phone = SecurityUtils.getCurrentUsername().get();
//        redisTemplate.boundListOps("" + nftId).


    }

    @Override
    public PrepayBO prePay(PayRequestDTO dto) throws IOException {
        PayDetailBO payDetailBO = new PayDetailBO();
        String prepayId = payHandler.handler(payDetailBO);

        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        String noStr = RandomUtil.randomString(32);

        // 小程序调起支付API
        PrepayBO prepayBO = new PrepayBO();
        prepayBO.setAppId("wxb3982d59b8a5e644");
        prepayBO.setTimeStamp(timestamp);
        prepayBO.setNonceStr(noStr);
        StringBuilder sb = new StringBuilder("prepay_id=");
        prepayBO.setPrepayId(sb.append(prepayId).toString());
        prepayBO.setSignType("MD5");
        prepayBO.setPaySign("");
        return prepayBO;
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(Instant.now().getEpochSecond());
    }
}
