package com.tencent.nft.core.security.oauth;

import com.tencent.nft.core.security.handler.CustomizeAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService appletsUserDetailsService;

    @Autowired
    private CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private OAuth2WebResponseExceptionTranslator oAuth2WebResponseExceptionTranslator;

    private final String OAUTH_TOKEN_PREFIX = "app:oauth:";

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        super.configure(endpoints);
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(appletsUserDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.POST)
                .pathMapping("/oauth/token", "/app/oauth/token")
                .tokenServices(tokenServices())
                .tokenStore(redisTokenStore())
                .exceptionTranslator(oAuth2WebResponseExceptionTranslator)
        ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
//                .allowFormAuthenticationForClients()
                .authenticationEntryPoint(authenticationEntryPoint);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    @Bean
    public DefaultTokenServices tokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(redisTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(2));
        tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7));
        return tokenServices;
    }

    @Bean
    public TokenStore redisTokenStore(){
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix(OAUTH_TOKEN_PREFIX);
        redisTokenStore.setSerializationStrategy(new JdkSerializationStrategy());
        return redisTokenStore;
    }

}
