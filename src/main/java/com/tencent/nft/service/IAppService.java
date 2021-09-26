package com.tencent.nft.service;

import com.tencent.nft.entity.app.vo.CollectionVO;
import com.tencent.nft.entity.pay.PayRequestDTO;
import com.tencent.nft.entity.pay.PrepayBO;
import com.tencent.nft.entity.security.WxUser;

import java.io.IOException;
import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/23
 * @description:
 */
public interface IAppService {

    List<CollectionVO> myLibrary();

    void reserve(String nftId);

    PrepayBO prePay(PayRequestDTO dto) throws IOException;
}
