package com.tencent.nft.web.controller;

import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {


    @RequestMapping("/token")
    public ResponseResult login(){
        return ResponseUtil.success();
    }

}
