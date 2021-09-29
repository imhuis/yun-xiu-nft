package com.tencent.nft.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author: imhuis
 * @date: 2021/9/28
 * @description:
 */
@Configuration
@PropertySource("classpath:properties/chain.properties")
@ConfigurationProperties(prefix = "chain", ignoreUnknownFields = false)
public class ChainProperties {

    private String clientId;
    private String clientSecret;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
