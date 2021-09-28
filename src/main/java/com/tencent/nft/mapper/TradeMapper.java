package com.tencent.nft.mapper;

import com.tencent.nft.common.enums.pay.TradeState;
import com.tencent.nft.entity.pay.TradeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/26
 * @description:
 */
@Mapper
public interface TradeMapper {

    int insert(TradeInfo tradeInfo);

    int updateByTradeNo(TradeInfo tradeInfo);

    TradeInfo selectByTradeNo(String tradeNo);

    List<TradeInfo> select(TradeInfo tradeInfo);
}
