package com.tencent.nft.security.oauth;

import com.tencent.nft.security.handler.CustomizeAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

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

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        super.configure(resources);
        resources
                .resourceId("*")
                .stateless(true)
                .tokenStore(tokenStore)
                .tokenServices(tokenService)
//                .authenticationEntryPoint(oauthTokenExceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        ;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers().antMatchers("/app/**")
                .and()
                .authorizeRequests()
                .antMatchers("/app/oauth/**").permitAll()
//                .anyRequest().authenticated()
                .antMatchers("/app/**").authenticated()

                // .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
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
