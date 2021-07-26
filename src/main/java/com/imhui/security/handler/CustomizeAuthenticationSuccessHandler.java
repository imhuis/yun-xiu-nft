package com.imhui.security.handler;

import com.imhui.security.common.base.ResponseResult;
import com.imhui.security.common.base.ResponseUtil;
import com.imhui.security.security.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        ResponseResult<TokenInfo> responseResult = new ResponseResult();
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setToken(request.getSession().getId());
        responseResult.setData(tokenInfo);
        ResponseUtil.out(response, responseResult);
    }

}
