package com.imhui.security.handler;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imhui.security.common.base.ResponseResult;
import com.imhui.security.common.base.ResponseUtil;
import com.imhui.security.common.util.TokenUtil;
import com.imhui.security.core.security.bo.SecurityUser;
import com.imhui.security.core.security.bo.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 生成token
        String newToken = TokenUtil.generateToken();
        User user = (User) authentication.getPrincipal();

        Set<? extends GrantedAuthority> authorities = authentication.getAuthorities().stream().collect(Collectors.toSet());


        SecurityUser securityUser = new SecurityUser();
//        securityUser.setUsername(user.get());
//        securityUser.setSessionId(user.getDetails());
//        redisTemplate.opsForHash().putAll(newToken, BeanUtil.beanToMap(securityUser));

        // 缓存保存token信息
        Duration expires = Duration.ofMinutes(60);
        BoundHashOperations<String,String,Object> boundHashOps = redisTemplate.boundHashOps(newToken);
        boundHashOps.putAll(BeanUtil.beanToMap(securityUser));
        redisTemplate.expire(newToken, expires);

        ResponseResult<TokenInfo> responseResult = new ResponseResult();
        TokenInfo tokenInfo = new TokenInfo(newToken, expires.getSeconds());
//        tokenInfo.setToken(request.getSession().getId());

        responseResult.setData(tokenInfo);
        ResponseUtil.out(response, responseResult);
    }

}
