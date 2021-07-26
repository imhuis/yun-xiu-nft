package com.imhui.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imhui.security.common.base.ResponseResult;
import com.imhui.security.common.base.ResponseUtil;
import com.imhui.security.common.exception.CaptchaValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ResponseResult responseResult = new ResponseResult();

        if (e instanceof LockedException){
            // 账户被锁
        }else if (e instanceof CredentialsExpiredException){
            // 密码过期
        }else if (e instanceof AccountExpiredException){
            // 账户过期
        }else if (e instanceof DisabledException){
            // 账户禁用
        }else if (e instanceof BadCredentialsException){
            // 用户名密码错误
        }else if (e instanceof CaptchaValidateException){
            responseResult.setMessage(e.getMessage());
        }
//        response.getWriter().write(objectMapper.writeValueAsString(ResponseUtil.success()));
        ResponseUtil.out(response, responseResult);
    }
}
