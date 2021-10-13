package com.tencent.nft.service;

import com.tencent.nft.common.base.PageBean;
import com.tencent.nft.entity.nft.dto.NftListQueryDTO;
import com.tencent.nft.entity.pay.dto.OrderQueryDTO;
import com.tencent.nft.entity.pay.vo.OrderListVO;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/10/8
 * @description:
 */
public interface IOrderService {

    PageBean<List<OrderListVO>> getOrderList(Integer page, Integer size, OrderQueryDTO orderQueryDTO);

}
