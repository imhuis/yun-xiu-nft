package com.tencent.nft.core.security;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: imhuis
 * @date: 2021/9/25
 * @description:
 */
@SpringBootTest
public class EncryptPassword {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void encryptor(){
        System.out.println(stringEncryptor.encrypt("AhBcosDb_1!$"));
        System.out.println(stringEncryptor.encrypt("password"));
        System.out.println(stringEncryptor.encrypt("GbPeB9cAGNNuT+5_HMRm"));
    }
}