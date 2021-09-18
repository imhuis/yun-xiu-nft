package com.tencent.nft.web.controller.app;

import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.common.enums.ResponseCodeEnum;
import com.tencent.nft.entity.app.WxResolvePhoneFormDTO;
import com.tencent.nft.service.IAppService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IAppService appService;

    /**
     * wx.login 接口 = 获取手机号
     * @param dto
     * @return
     */
    @RequestMapping("/token")
    public ResponseResult login(@RequestBody WxResolvePhoneFormDTO dto){
        try {
            String token = appService.appLogin(dto);
            return ResponseUtil.success(token);
        }catch (Exception e){
            return ResponseUtil.fail(ResponseCodeEnum.FAILD);
        }
    }


}
