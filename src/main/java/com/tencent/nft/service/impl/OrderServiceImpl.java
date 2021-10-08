package com.tencent.nft.service.impl;

import com.tencent.nft.mapper.pay.OrderMapper;
import com.tencent.nft.service.IOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: imhuis
 * @date: 2021/10/8
 * @description:
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    private OrderMapper orderMapper;


}
