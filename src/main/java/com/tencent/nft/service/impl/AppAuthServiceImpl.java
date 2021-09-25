package com.tencent.nft.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.util.UUIDUtil;
import com.tencent.nft.common.util.WxResolveDataUtils;
import com.tencent.nft.common.util.otpcode.OtpCodeHandler;
import com.tencent.nft.entity.app.dto.WxResolvePhoneFormDTO;
import com.tencent.nft.entity.app.dto.WxUserProfileFormDTO;
import com.tencent.nft.entity.security.User;
import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.mapper.UserMapper;
import com.tencent.nft.mapper.WxUserMapper;
import com.tencent.nft.service.IAppAuthService;
import com.tencent.nft.service.handler.WeChatOpenIdByJsCodeLoader;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description:
 */
@Service
@Slf4j
public class AppAuthServiceImpl implements IAppAuthService {

    private static Logger LOG = LoggerFactory.getLogger(AppAuthServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private WeChatOpenIdByJsCodeLoader weChatOpenIdByJsCodeLoader;

    @Resource
    private WxUserMapper wxUserMapper;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorizationServerTokenServices tokenServices;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${oauth2.basic}")
    private String oauthString;

    @Override
    public WeChatOpenIdByJsCodeLoader.WxLoginResult wxLogin(String jsCode) {
        return weChatOpenIdByJsCodeLoader.load(jsCode);
    }

    @Transactional
    @Override
    public void updateWxUserProfile(WxUserProfileFormDTO dto) {
        String openId = dto.getOpenId();
        Optional<WxUser> wxUserOptional = wxUserMapper.selectFullByOpenId(openId);

        WxUser wxUser = new WxUser();
        wxUser.setNickname(dto.getNickName());
        wxUser.setGender(dto.getGender());
        wxUser.setAvatarUrl(dto.getAvatarUrl());
        wxUser.setCity(dto.getCity());
        wxUser.setProvince(dto.getProvince());
        wxUser.setCountry(dto.getCountry());
        wxUser.setUpdateTime(LocalDateTime.now());
        // 已存在，更新
        wxUserOptional.ifPresent(w -> wxUserMapper.updateByOpenid(wxUser));
        if (wxUserOptional.isEmpty()){
            wxUser.setOtpSecret(new OtpCodeHandler().generateSecret());
            wxUser.setOpenId(dto.getOpenId());
            wxUser.setCreateTime(LocalDateTime.now());
            wxUserMapper.insert(wxUser);
        }
    }

    @Override
    public Object appLogin(WxResolvePhoneFormDTO dto) {
        // 解析完手机号只有（openid、phone）
        WxUser wxAccountInfoObject = decryptPhone(dto);
        String phone = wxAccountInfoObject.getPhone();
        String openId = wxAccountInfoObject.getOpenId();
        // 查询当前登录用户的手机是否存在，存在直接登录
        Optional<WxUser> wxUserOptional = wxUserMapper.selectFullByPhone(phone);
        WxUser wxUser = null;
        // 找不到这个手机号的微信用户
        if (wxUserOptional.isEmpty()){
            wxUser = reNewAccount(phone, openId);
        }else {
            wxUser = wxUserOptional.get();
        }

        // 找到当前手机号，代码登录程序，创建token 返回token
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("username", wxUser.getPhone());
        map.add("password", wxUser.getOtpSecret());

        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/app/oauth/token").build().toUri();
        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", oauthString)
                .accept(MediaType.APPLICATION_JSON)
                .body(map);
        log.info("req: " + requestEntity.getHeaders());
        ResponseEntity<ResponseResult> tokenResponseEntity = restTemplate.exchange(requestEntity, ResponseResult.class);
        if (tokenResponseEntity.getStatusCode().is2xxSuccessful()){
            return tokenResponseEntity.getBody().getData();
        }
        return null;
    }

//    private String loadPassword() {
//        String clearOPTSecret = TUSIBase64Utils.decrypt(weChatUser.getOtpSecret());
//        try {
//            return new OtpCodeHandler().generateCode(clearOPTSecret);
//        } catch (OtpCodeException e) {
//            return null;
//        }
//    }

    @Override
    public Object testLogin(String phone) {
        WxUser wxUser = wxUserMapper.selectFullByPhone(phone).get();

        // 使用 DefaultTokenServices.createAccessToken 创建token
//        Map<String, String> authorizationParameters = new HashMap<>();
//        authorizationParameters.put("username", phone);
//        authorizationParameters.put("password", wxUser.getOtpSecret());
//        authorizationParameters.put("scope", "app");
//        authorizationParameters.put("client_id", "c64a0005-aaf6-46a2-a452-c0000705459b");
//        authorizationParameters.put("client_secret", "df8a0a1b-2db5-4ae8-b517-373c15e71483");
//        authorizationParameters.put("grant", "password");
//
//
//        OAuth2Request authorizationRequest = new OAuth2Request(
//                authorizationParameters, "c64a0005-aaf6-46a2-a452-c0000705459b",
//                null, true, null, null, "",
//                null, null);
//
//        org.springframework.security.core.userdetails.User userPrincipal =
//                new org.springframework.security.core.userdetails.User(phone, wxUser.getOtpSecret(), Collections.emptyList());
//
//        final UsernamePasswordAuthenticationToken wxLoginToken =
//                new UsernamePasswordAuthenticationToken(userPrincipal, wxUser.getOtpSecret());
////        Authentication authentication = authenticationManager.authenticate(wxLoginToken);
//
//        OAuth2Authentication authenticationRequest = new OAuth2Authentication(authorizationRequest, wxLoginToken);
//        authenticationRequest.setAuthenticated(true);
//
//        OAuth2AccessToken accessToken = tokenServices.createAccessToken(authenticationRequest);
//        return accessToken;

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("username", phone);
        map.add("password", wxUser.getOtpSecret());
        map.add("grant_type", "password");

        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/app/oauth/token").build().toUri();
        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", oauthString)
                .accept(MediaType.APPLICATION_JSON)
                .body(map);
        ResponseEntity<ResponseResult> tokenResponseEntity = restTemplate.exchange(requestEntity, ResponseResult.class);
        if (tokenResponseEntity.getStatusCode().is2xxSuccessful()){
            return tokenResponseEntity.getBody().getData();
        }
        return null;

    }

    @Override
    public WxUser getMyInformation(String phone) {
        return wxUserMapper.selectByPhone(phone).get();
    }


    @Transactional
    public WxUser reNewAccount(String phone, String openid) {
        // 生成用户uuid，先插入 s_user
        final String uuid = UUIDUtil.generateUUID();

        WxUser wxUser;
        // openid 复查一遍
        Optional<WxUser> wxUserOptional = wxUserMapper.selectFullByOpenId(openid);
        // 如果openid查询还为null 则说明 s_wx_user表也没有当前数据
        if (wxUserOptional.isEmpty()){
            wxUser = new WxUser();
            wxUser.setUserId(uuid);
            wxUser.setOpenId(openid);
            wxUser.setPhone(phone);
            wxUser.setOtpSecret(new OtpCodeHandler().generateSecret());
            // 插入wxUser(openId,UserId)
            User newUser = new User();
            newUser.setUserId(uuid);
            newUser.setPhone(phone);
            newUser.setNickname(wxUser.getNickname());
            userMapper.insert(newUser);
            wxUserMapper.insert(wxUser);
        }else {
            // 可以通过openid查询到说明存在该条记录
            wxUser = wxUserOptional.get();
            wxUser.setPhone(phone);
            wxUserMapper.updateByOpenid(wxUser);
        }
        return wxUser;
    }


    /**
     * 解析加密手机
     * @param wxResolvePhoneFormDTO
     * @return
     */
    private WxUser decryptPhone(WxResolvePhoneFormDTO wxResolvePhoneFormDTO) {

        WxUser wxUser = new WxUser();

//        WeChatOpenIdByJsCodeLoader.WxLoginResult wxLoginResult = weChatOpenIdByJsCodeLoader.load(wxResolvePhoneFormDTO.getJsCode());
//        if (StringUtils.isNotBlank(wxLoginResult.getOpenId())){
//            wxUser.setOpenId(wxLoginResult.getOpenId());
//        }
        String sessionKey = wxResolvePhoneFormDTO.getSessionKey();
        LOG.info("method[resolveWeChatAccountInfo] session>{}", sessionKey);

        // 解析encrypted data获取用户手机号（包括openid）
        String resolveData = new String(WxResolveDataUtils.decrypt(wxResolvePhoneFormDTO.getEncryptedData(), sessionKey, wxResolvePhoneFormDTO.getIv()));
        try {
            /*{"phoneNumber":"11111111111","purePhoneNumber":"11111111111","countryCode":"86","watermark":{"timestamp":1554949910,"appid":"wxc418d44a80e38c7b"}}*/
            JsonNode jsonNode = objectMapper.readTree(resolveData);
            wxUser.setPhone(jsonNode.get("phoneNumber").asText());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return wxUser;
    }

}
