/*
 * Copyright © 1998 - 2021 Tencent. All Rights Reserved
 * www.tencent.com
 * All rights reserved.
 */
package com.tencent.nft.service.handler;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;

/**
 * @author :bobzbfeng
 * @date :2021-04-08 12:33
 * @description :获取openId（通过JScode获取openId）
 */
@Component
@Slf4j
public class WeChatOpenIdByJsCodeLoader {

    @Autowired
    private RestTemplate restTemplate;

    private static Logger LOG = LoggerFactory.getLogger(WeChatOpenIdByJsCodeLoader.class);

    private static final String SESSION_KEY = "session_key";
    private static final String OPEN_ID_KEY = "openid";

    private static final String WX_JSCODE_URL = "https://api.weixin.qq.com/sns/jscode2session";

    public WxLoginResult load(String jsCode) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(WX_JSCODE_URL)
                .queryParam("appid", "wxc7b46a61408f4797")
                .queryParam("secret", "4ec5247bfcdc36856e69c4b75f1bb6b4")
                .queryParam("grant_type", "authorization_code")
                .queryParam("js_code", jsCode);

        ResponseEntity<String> response = restTemplate.getForEntity(uriComponentsBuilder.toUriString(), String.class);

        if (response.getStatusCode().is2xxSuccessful()){
            String responseBody = response.getBody();
            log.info("responseTxt:{}", responseBody);

            JSONObject jsonObject = JSONObject.fromObject(responseBody);
//            final Integer errorCode = (Integer)jsonObject.get("errcode");
//            if(errorCode == null){
            // session_key
            String sessionKey = (String)jsonObject.get(SESSION_KEY);
            // openid
            String openId = (String)jsonObject.get(OPEN_ID_KEY);
            return new WxLoginResult(openId,sessionKey);
//            }else {
//                return new WxLoginResult(errorCode,"系统繁忙，请刷新后重试");
//            }
        }
        return null;
    }


    public class WxLoginResult implements Serializable {
        private static final long serialVersionUID = 7624975778972786935L;

        private String openId;

        private String sessionKey;

        public WxLoginResult(String openId, String sessionKey) {
            this.openId = openId;
            this.sessionKey = sessionKey;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getSessionKey() {
            return sessionKey;
        }

        public void setSessionKey(String sessionKey) {
            this.sessionKey = sessionKey;
        }
    }
}