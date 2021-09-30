package com.tencent.nft.service;

import com.tencent.nft.entity.pay.dto.PayRequestDTO;
import com.tencent.nft.entity.pay.bo.PrepayVO;

/**
 * @author: imhuis
 * @date: 2021/9/27
 * @description:
 */
public interface IPayService {

    /**
     * 下单购买，生成预支付订单参数
     * @param dto
     * @return
     * @throws Exception
     */
    PrepayVO order(PayRequestDTO dto) throws Exception;

    /**
     * 支付通知
     */
    void notifyApp(String resJson);

    /**
     * 退款
     */
    void refund();

    /**
     * 下载账单
     */
    void downloadBill();
}
