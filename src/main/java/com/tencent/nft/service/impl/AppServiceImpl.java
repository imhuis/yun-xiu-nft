package com.tencent.nft.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.tencent.nft.common.util.UUIDUtil;
import com.tencent.nft.common.util.WXPayUtil;
import com.tencent.nft.core.config.WxGroupConfig;
import com.tencent.nft.core.security.SecurityUtils;
import com.tencent.nft.entity.app.vo.CollectionVO;
import com.tencent.nft.entity.pay.bo.PayDetailBO;
import com.tencent.nft.entity.pay.PayRequestDTO;
import com.tencent.nft.entity.pay.bo.PrepayBO;
import com.tencent.nft.mapper.NftProductMapper;
import com.tencent.nft.service.IAppService;
import com.tencent.nft.service.handler.WechatPayHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
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
    private NftProductMapper productMapper;

    @Autowired
    private WechatPayHandler payHandler;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private WxGroupConfig wxGroupConfig;

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
    public PrepayBO prePay(PayRequestDTO dto) throws Exception {
        PayDetailBO payDetailBO = new PayDetailBO();
        payDetailBO.setTotal(1);
        payDetailBO.setDescription("数字藏品-" + dto.getNftId());
        payDetailBO.setTradeNo(UUIDUtil.generateUUID());
        payDetailBO.setOpenId(dto.getOpenId());
        String prepayId = payHandler.handler(payDetailBO);

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
        String sign = WXPayUtil.generateSignature(wxPayMap, "jiangsuanhuanglingyukejiyouxian1");

        // 插入订单表


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

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(Instant.now().getEpochSecond());
    }
}
