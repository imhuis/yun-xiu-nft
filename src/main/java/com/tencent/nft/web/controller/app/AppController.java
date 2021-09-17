package com.tencent.nft.web.controller.app;

import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.entity.app.WxResolvePhoneFormDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description:
 */
@RestController
@RequestMapping("/app")
public class AppController {

    // 手机号登录
    @RequestMapping("/token")
    public ResponseResult login(@RequestBody WxResolvePhoneFormDTO dto){

        return ResponseUtil.success();
    }


}
