package com.tencent.nft.core.config;

import com.tencent.nft.common.properties.WxGroupProperties;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.security.PrivateKey;

/**
 * @author: imhuis
 * @date: 2021/9/28
 * @description:
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(WxGroupProperties.class)
public class WxPayConfig {

    @Autowired
    private WxGroupProperties wxGroupProperties;

    @Bean
    public HttpClient wechatPayHttpClient() throws IOException {

        final String apiV3Key = wxGroupProperties.getApiKey();

//        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new FileInputStream("D:\\data\\pay\\apiclient_key.pem"));

        ClassPathResource classPathResource = new ClassPathResource("pay/apiclient_key.pem");
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(classPathResource.getInputStream());

        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(wxGroupProperties.getWxPayMchId(), new PrivateKeySigner(wxGroupProperties.getSerialNumber(), merchantPrivateKey)),
                apiV3Key.getBytes("utf-8"));

        return WechatPayHttpClientBuilder.create()
                .withMerchant(wxGroupProperties.getWxPayMchId(), wxGroupProperties.getSerialNumber(), merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier)).build();
    }
}
