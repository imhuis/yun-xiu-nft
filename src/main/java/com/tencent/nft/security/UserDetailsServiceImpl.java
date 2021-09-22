package com.tencent.nft.security;

import com.tencent.nft.mapper.UserMapper;
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
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.info("UserDetails [loadUserByUsername]: {}", s);
        // 邮箱
        if (new EmailValidator().isValid(s, null)){
            return userMapper.findUserByEmail(s)
                    .map(user -> createSpringSecurityUser(s, user))
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + s + "was not found"));
        }
        if (Character.isDigit(s.charAt(0))){
            return userMapper.findUserByPhone(s)
                    .map(user -> createSpringSecurityUser(s, user))
                    .orElseThrow(() -> new UsernameNotFoundException("User with phone " + s + "was not found"));
        }

        return userMapper.findUserByUserName(s)
                .map(user -> createSpringSecurityUser(s, user))
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + s + "was not found"));
    }

    private User createSpringSecurityUser(String login, com.tencent.nft.entity.security.User user){
//        if (false){
//            throw new UserNotActivatedException("");
//        }
        List<String> authorityString = null;
//        AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString);

//        List<GrantedAuthority> grantedAuthorities = authorityString
//                .stream().map(authority -> new SimpleGrantedAuthority(authority))
//                .collect(Collectors.toList());
        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }

}
