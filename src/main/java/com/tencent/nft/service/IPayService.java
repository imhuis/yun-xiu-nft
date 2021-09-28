package com.tencent.nft.service;

import com.tencent.nft.entity.pay.dto.PayRequestDTO;
import com.tencent.nft.entity.pay.bo.PrepayBO;

/**
 * @author: imhuis
 * @date: 2021/9/27
 * @description:
 */
public interface IPayService {

    PrepayBO prePay(PayRequestDTO dto) throws Exception;

    void notifyApp(String resJson);
}
