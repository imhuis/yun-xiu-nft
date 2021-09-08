package com.tencent.nft.security;

import com.tencent.nft.domain.security.User;
import com.tencent.nft.security.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void initUser(){
        User newUser = new User();
        newUser.setUserName("zhangyang");
        newUser.setPassword(passwordEncoder.encode("123456"));
        userDao.save(newUser);
    }

    @Test
    public void test(){
        System.out.println(passwordEncoder.encode("df8a0a1b-2db5-4ae8-b517-373c15e71483"));
    }
}
