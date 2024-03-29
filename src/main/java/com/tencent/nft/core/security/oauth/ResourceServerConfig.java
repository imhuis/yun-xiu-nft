package com.tencent.nft.core.security.oauth;

import com.tencent.nft.core.security.handler.CustomizeAccessDeniedHandler;
import com.tencent.nft.core.security.handler.CustomizeAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private DefaultTokenServices tokenService;

    @Autowired
    private CustomizeAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomizeAuthenticationEntryPoint oauthTokenExceptionEntryPoint;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .resourceId("*")
                .stateless(true)
                .tokenStore(tokenStore)
                .tokenServices(tokenService)
                .authenticationEntryPoint(oauthTokenExceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        ;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .antMatchers("/app/**")
                .and()
                .authorizeRequests()
                .antMatchers("/app/oauth/**").permitAll()
                .antMatchers("/app/public/**").permitAll()
                .antMatchers("/app/**").authenticated()

                .and()
                .csrf()
                .disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(oauthTokenExceptionEntryPoint)
//                .accessDeniedHandler(oauthAccessDeniedHandler)
        ;
    }
}
