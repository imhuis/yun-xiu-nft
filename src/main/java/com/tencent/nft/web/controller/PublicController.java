package com.tencent.nft.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * 微信支付成功回调接口
     * @return
     */
    @RequestMapping(value = "/public/notify/wxpay/pay.yy", method = RequestMethod.POST)
    public String notifyAction(){

        return "";
    }
}
