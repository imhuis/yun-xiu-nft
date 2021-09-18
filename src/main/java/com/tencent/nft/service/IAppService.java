package com.tencent.nft.service;

import com.tencent.nft.entity.app.WxResolvePhoneFormDTO;
import com.tencent.nft.entity.security.WxUser;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description:
 */
public interface IAppService {

    String appLogin(WxResolvePhoneFormDTO dto);

}
