package com.tencent.nft.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: imhuis
 * @date: 2021/9/26
 * @description:
 */
@RestController
public class PublicController {


    /**
     * 支付成功回调接口
     * @return
     */
    @RequestMapping("/public/notify/wxpay/pay.yy")
    public String notifyAction(){
        return "";
    }
}
