package com.tencent.nft.core.security;

import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.mapper.WxUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author: imhuis
 * @date: 2021/9/22
 * @description:
 */
@SpringBootTest
public class WxUserTest {


    @Resource
    private WxUserMapper wxUserMapper;

    @Test
    public void test(){
        Optional<WxUser> wxUserOptional = wxUserMapper.selectFullByOpenId("");
        System.out.println(wxUserOptional.isPresent());
        System.out.println(wxUserOptional.isEmpty());
    }
}
