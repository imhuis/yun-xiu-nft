package com.tencent.nft.mapper.pay;

import com.tencent.nft.entity.pay.Order;
import com.tencent.nft.entity.pay.PreviouslyOrder;
import com.tencent.nft.entity.pay.dto.OrderQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/29
 * @description: 预订单
 */
@Mapper
public interface OrderMapper {

    /**
     * 生成预订单
     * @param previouslyOrder
     * @return
     */
    int insertPreviouslyOrder(PreviouslyOrder previouslyOrder);

    /**
     * 根据业务号查询预订单
     * @param tradeNo
     * @return
     */
    PreviouslyOrder selectPreviouslyOrderByTradeNo(String tradeNo);

    List<PreviouslyOrder> selectPreviouslyOrderByNftIdAndPayer(@Param("nftId") String nftId, @Param("payer") String payer);

    int insertOrder(Order order);

    List<Order> selectOrder(OrderQueryDTO orderQueryDTO);



}
