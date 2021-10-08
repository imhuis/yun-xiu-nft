package com.tencent.nft.service;

import com.tencent.nft.entity.app.dto.WxResolvePhoneFormDTO;
import com.tencent.nft.entity.app.dto.WxUserProfileFormDTO;
import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.service.handler.WeChatOpenIdByJsCodeLoader;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description:
 */
public interface IAppAuthService {

    WeChatOpenIdByJsCodeLoader.WxLoginResult wxLogin(String jsCode);

    void updateWxUserProfile(WxUserProfileFormDTO wxUserProfileFormDTO);

    Object appLogin(WxResolvePhoneFormDTO dto);

    Object testLogin(String phone);

    WxUser getMyInformation(String phone);
}
