package com.tencent.nft.security.handler;

import cn.hutool.core.bean.BeanUtil;
import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.common.util.TokenUtil;
import com.tencent.nft.security.bo.TokenInfo;
import com.tencent.nft.domain.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 生成token
        String newToken = TokenUtil.generateToken();
        User user = (User) authentication.getPrincipal();

        Set<? extends GrantedAuthority> authorities = authentication.getAuthorities().stream().collect(Collectors.toSet());


        SecurityUser securityUser = new SecurityUser();
//        securityUser.setUsername(user.get());
//        securityUser.setSessionId(user.getDetails());

        // 缓存保存token信息
        Duration expires = Duration.ofMinutes(60);
        BoundHashOperations<String,String,Object> boundHashOps = redisTemplate.boundHashOps(newToken);
        boundHashOps.putAll(BeanUtil.beanToMap(securityUser));
        boundHashOps.expire(expires);

        ResponseResult<TokenInfo> responseResult = new ResponseResult();
        responseResult.setMessage("Login success");
        TokenInfo tokenInfo = new TokenInfo(newToken, expires.getSeconds());
//        tokenInfo.setToken(request.getSession().getId());
        responseResult.setData(tokenInfo);
        ResponseUtil.out(response, responseResult);
    }

}
