package com.tencent.nft.service;

import com.tencent.nft.entity.pay.dto.PayRequestDTO;
import com.tencent.nft.entity.pay.bo.PrepayBO;

/**
 * @author: imhuis
 * @date: 2021/9/27
 * @description:
 */
public interface IPayService {

    /**
     * 预购买，生成支付参数
     * @param dto
     * @return
     * @throws Exception
     */
    PrepayBO prePay(PayRequestDTO dto) throws Exception;

    /**
     * 支付通知
     */
    void notifyApp(String resJson);

    /**
     * 退款
     */
    void refund();
}
