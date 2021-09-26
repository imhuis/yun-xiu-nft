package com.tencent.nft.core.config;

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.client.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;

/**
 * @author: imhuis
 * @date: 2021/9/26
 * @description:
 */
@Configuration
public class WechatPayClientConfig {

    @Bean
    public HttpClient wechatPayHttpClient() throws FileNotFoundException, UnsupportedEncodingException {
        final String apiV3Key = "jiangsuanhuanglingyukejiyouxian1";

        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new FileInputStream("D:\\data\\pay\\apiclient_key.pem"));
        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials("1503971171", new PrivateKeySigner("676B6612E9F374CE8AD1262F4D4F23B104CD5125", merchantPrivateKey)),
                apiV3Key.getBytes("utf-8"));

        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant("1503971171", "676B6612E9F374CE8AD1262F4D4F23B104CD5125", merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier));

        HttpClient httpClient = builder.build();
        return httpClient;
    }


}
