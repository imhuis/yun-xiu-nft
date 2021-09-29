package com.tencent.nft.mapper.pay;

import com.tencent.nft.entity.pay.PreOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: imhuis
 * @date: 2021/9/29
 * @description:
 */
@Mapper
public interface OrderMapper {

    /**
     * 生成预订单
     * @param preOrder
     * @return
     */
    int insert(PreOrder preOrder);

    /**
     * 根据业务号查询预订单
     * @param tradeNo
     * @return
     */
    PreOrder selectByTradeNo(String tradeNo);

}
