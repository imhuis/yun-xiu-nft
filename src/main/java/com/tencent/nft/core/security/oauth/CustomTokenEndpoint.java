package com.tencent.nft.core.security.oauth;

import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
@RestController
public class CustomTokenEndpoint {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @RequestMapping(value = "/app/oauth/token", method= RequestMethod.GET)
    public ResponseResult<OAuth2AccessToken> getAccessToken() {
        return ResponseUtil.build(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @RequestMapping(value = "/app/oauth/token", method= RequestMethod.POST)
    public ResponseResult<OAuth2AccessToken> postAccessToken(Principal principal,
                                                            @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return ResponseUtil.success(accessToken);
    }

}
