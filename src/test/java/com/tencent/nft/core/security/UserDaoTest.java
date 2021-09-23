package com.tencent.nft.core.security;

import com.tencent.nft.entity.security.User;
import com.tencent.nft.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void initUser(){
        User newUser = new User();
        newUser.setUsername("zhangyang");
        newUser.setPassword(passwordEncoder.encode("123456"));
        userMapper.insert(newUser);
    }

    @Test
    public void test(){
        System.out.println(passwordEncoder.encode("df8a0a1b-2db5-4ae8-b517-373c15e71483"));
        System.out.println(passwordEncoder.encode("Abcd12345!#"));
    }
}
