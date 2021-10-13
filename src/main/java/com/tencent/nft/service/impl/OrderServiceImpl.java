package com.tencent.nft.service.impl;

import com.tencent.nft.common.base.PageBean;
import com.tencent.nft.entity.pay.dto.OrderQueryDTO;
import com.tencent.nft.entity.pay.vo.OrderListVO;
import com.tencent.nft.mapper.pay.OrderMapper;
import com.tencent.nft.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/10/8
 * @description:
 */
@Service
public class OrderServiceImpl implements IOrderService {

    final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    private OrderMapper orderMapper;


    @Override
    public PageBean<List<OrderListVO>> getOrderList(Integer page, Integer size, OrderQueryDTO orderQueryDTO) {

        orderMapper.selectOrder(orderQueryDTO);
        return null;
    }
}
