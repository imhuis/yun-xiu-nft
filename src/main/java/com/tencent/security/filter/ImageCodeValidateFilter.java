package com.tencent.security.filter;

import com.tencent.security.common.constant.SecurityConstants;
import com.tencent.security.common.exception.CaptchaValidateException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
@Component
public class ImageCodeValidateFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ImageCodeValidateFilter.class);

    private static final String CAPTCHA_VALUE = "captcha";

    private static final String LOGIN_URL = "/login";

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // request.getRequestURI().endsWith(LOGIN_URL)
        logger.debug("[ImageCodeValidateFilter] request uri:{}", request.getRequestURI());
        logger.debug("result:{}",StringUtils.equalsIgnoreCase(LOGIN_URL, request.getRequestURI()) && StringUtils.equalsIgnoreCase(request.getMethod(), HttpMethod.POST.name()));
        if (StringUtils.equalsIgnoreCase(LOGIN_URL, request.getRequestURI()) && StringUtils.equalsIgnoreCase(request.getMethod(), HttpMethod.POST.name())){
            try{
                validate(new ServletWebRequest(request));
            }catch (AuthenticationException e){
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), CAPTCHA_VALUE);
        if (StringUtils.isEmpty(codeInRequest)){
            throw new CaptchaValidateException("验证码不能为空");
        }
        String s = (String) servletWebRequest.getRequest().getSession().getAttribute(SecurityConstants.SESSION_KEY_IMAGE_CODE);
        if (Objects.isNull(s)){
            throw new CaptchaValidateException("验证码不存在");
        }
        // 判断验证码过期
        if (!StringUtils.equalsIgnoreCase(s, codeInRequest)){
            throw new CaptchaValidateException("验证码错误");
        }
        servletWebRequest.getRequest().getSession().removeAttribute(SecurityConstants.SESSION_KEY_IMAGE_CODE);

    }
}
