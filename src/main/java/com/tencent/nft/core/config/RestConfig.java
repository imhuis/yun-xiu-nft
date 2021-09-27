package com.tencent.nft.core.config;

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.PrivateKey;
import java.time.Duration;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description:
 */
@Configuration
public class RestConfig {

    @Autowired
    private WxGroupConfig wxGroupConfig;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.requestFactory(OkHttp3ClientHttpRequestFactory::new)
                .setConnectTimeout(Duration.ofMillis(500))
                .setReadTimeout(Duration.ofMillis(10000))
                .build();
    }

    @Bean
    public HttpClient wechatPayHttpClient() throws IOException {

        final String apiV3Key = wxGroupConfig.getApiKey();

        ClassPathResource classPathResource = new ClassPathResource("pay/apiclient_key.pem");
//        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new FileInputStream("D:\\data\\pay\\apiclient_key.pem"));
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(classPathResource.getInputStream());

        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(wxGroupConfig.getWxPayMchId(), new PrivateKeySigner(wxGroupConfig.getSerialNumber(), merchantPrivateKey)),
                apiV3Key.getBytes("utf-8"));

        return WechatPayHttpClientBuilder.create()
                .withMerchant(wxGroupConfig.getWxPayMchId(), wxGroupConfig.getSerialNumber(), merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier)).build();
    }
}
