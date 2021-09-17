package com.tencent.nft.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.nft.common.util.WxResolveDataUtils;
import com.tencent.nft.entity.WxAccountInfoObject;
import com.tencent.nft.entity.app.WxResolvePhoneFormDTO;
import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.mapper.WxUserMapper;
import com.tencent.nft.service.IAppService;
import com.tencent.nft.service.handler.WeChatOpenIdByJsCodeLoader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description:
 */
@Service
public class AppServiceImpl implements IAppService {

    private static Logger LOG = LoggerFactory.getLogger(AppServiceImpl.class);

    @Autowired
    private WeChatOpenIdByJsCodeLoader weChatOpenIdByJsCodeLoader;

    @Resource
    private WxUserMapper wxUserMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String appLogin(WxResolvePhoneFormDTO dto) {
        WxAccountInfoObject wxAccountInfoObject = decryptPhone(dto);
        String openId = wxAccountInfoObject.getOpenId();
        String phone = wxAccountInfoObject.getPhone();
        WxUser wxUser = wxUserMapper.selectByPhone(phone);
        if (wxUser == null){
            wxAccountInfoObject.setNickName(dto.getNickName());
            wxAccountInfoObject.setAvatarUrl(dto.getAvatarUrl());
            createNewAccount(wxAccountInfoObject);
        }
        return "";
    }


    @Transactional
    public WxUser createNewAccount(WxAccountInfoObject baseDataDTO) {
        // 插入两张用户表
        String openId = baseDataDTO.getOpenId();
        String phone = baseDataDTO.getPhone();
        String nickName = baseDataDTO.getNickName();
        Integer gender = baseDataDTO.getGender();
        String city = baseDataDTO.getCity();
        String province = baseDataDTO.getProvince();
        String country = baseDataDTO.getCountry();
        String avatarUrl = baseDataDTO.getAvatarUrl();
        // WxUser对象创建, 插入数据库
        WxUser wxUser = new WxUser(openId, phone, nickName, gender, city, province, country, avatarUrl);
        wxUserMapper.insert(wxUser);
        return wxUser;
    }


    /**
     * 解析加密手机
     * @param wxResolvePhoneFormDTO
     * @return
     */
    private WxAccountInfoObject decryptPhone(WxResolvePhoneFormDTO wxResolvePhoneFormDTO) {

        WxAccountInfoObject wxobj = new WxAccountInfoObject();

        WeChatOpenIdByJsCodeLoader.WxLoginResult wxLoginResult = weChatOpenIdByJsCodeLoader.load(wxResolvePhoneFormDTO.getJsCode());
        if (StringUtils.isNotBlank(wxLoginResult.getOpenId())){
            wxobj.setOpenId(wxLoginResult.getOpenId());
        }
        String sessionKey = wxLoginResult.getSessionKey();
        LOG.info("method[resolveWeChatAccountInfo] session>{}", sessionKey);

        // 解析encrypted data获取用户手机号（包括openid）
        String resolveData =new String(WxResolveDataUtils.decrypt(wxResolvePhoneFormDTO.getEncryptedData(), sessionKey, wxResolvePhoneFormDTO.getIv()));
        try {
            /*{"phoneNumber":"18852961663","purePhoneNumber":"18852961663","countryCode":"86","watermark":{"timestamp":1554949910,"appid":"wxc418d44a80e38c7b"}}*/
            JsonNode jsonNode = objectMapper.readTree(resolveData);
            wxobj.setPhone(jsonNode.get("phoneNumber").asText());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return wxobj;
    }

}
