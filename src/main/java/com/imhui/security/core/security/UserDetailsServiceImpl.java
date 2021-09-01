package com.imhui.security.core.security;

import com.imhui.security.repository.UserDao;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.info(s);
        // 邮箱
        if (new EmailValidator().isValid(s, null)){
            return userDao.findUserByEmail(s)
                    .map(user -> createSpringSecurityUser(s, user))
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + s + "was not found"));
        }
        if (Character.isDigit(s.charAt(0))){
            return userDao.findUserByPhone(s)
                    .map(user -> createSpringSecurityUser(s, user))
                    .orElseThrow(() -> new UsernameNotFoundException("User with phone " + s + "was not found"));
        }

        return userDao.findUserByUserName(s)
                .map(user -> createSpringSecurityUser(s, user))
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + s + "was not found"));
    }

    private User createSpringSecurityUser(String login, com.imhui.security.domain.User user){
//        if (false){
//            throw new UserNotActivatedException("");
//        }
        List<String> authorityString = null;
//        AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString);

//        List<GrantedAuthority> grantedAuthorities = authorityString
//                .stream().map(authority -> new SimpleGrantedAuthority(authority))
//                .collect(Collectors.toList());
        return new User(user.getUserName(), user.getPassword(), Collections.emptyList());
    }

}
