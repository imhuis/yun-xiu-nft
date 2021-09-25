package com.tencent.nft.core.security.oauth;

import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.mapper.WxUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author: imhuis
 * @date: 2021/9/22
 * @description:
 */
@Service
public class AppletsUserDetailsService implements UserDetailsService {

    @Resource
    private WxUserMapper wxUserMapper;

    @Override
    public UserDetails loadUserByUsername(String p) throws UsernameNotFoundException {
        return wxUserMapper.selectFullByPhone(p)
                .map(user -> createSpringSecurityUser(user))
                .orElseThrow(() -> new UsernameNotFoundException("User with phone " + p + "was not found"));
    }

    private User createSpringSecurityUser(WxUser user) {
        return new User(user.getPhone(), StringUtils.EMPTY, Collections.emptyList());
    }
}
