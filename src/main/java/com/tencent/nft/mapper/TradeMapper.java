package com.tencent.nft.mapper;

import com.tencent.nft.entity.pay.TradeInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: imhuis
 * @date: 2021/9/26
 * @description:
 */
@Mapper
public interface TradeMapper {

    int insert(TradeInfo tradeInfo);
}
