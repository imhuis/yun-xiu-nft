package com.imhui.security;

import com.imhui.security.domain.User;
import com.imhui.security.repository.UserDao;
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
}
