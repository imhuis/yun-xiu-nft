package com.tencent.nft.web.controller.app;

import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.common.enums.ResponseCodeEnum;
import com.tencent.nft.entity.app.WxResolvePhoneFormDTO;
import com.tencent.nft.entity.app.WxUserProfileFormDTO;
import com.tencent.nft.service.IAppAuthService;
import com.tencent.nft.service.handler.WeChatOpenIdByJsCodeLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description:
 */
@RestController
@RequestMapping("/app/public")
public class AppAuthController {

    @Autowired
    private IAppAuthService appAuthService;

    @RequestMapping(value = "/wx_login", method = RequestMethod.POST)
    public ResponseResult getSessionCode(@RequestParam("js_code") String jsCode){
        WeChatOpenIdByJsCodeLoader.WxLoginResult wxLoginResult = appAuthService.wxLogin(jsCode);
        if (wxLoginResult == null){
            return ResponseUtil.define(-1, "js_code 过期");
        }
        return ResponseUtil.success(wxLoginResult);
    }

    /**
     * 更新用户信息
     * @param wxUserProfileFormDTO
     * @return
     */
    @RequestMapping(value = "/user_profile", method = RequestMethod.POST)
    public ResponseResult initUser(@RequestBody @Validated WxUserProfileFormDTO wxUserProfileFormDTO){
        appAuthService.updateWxUserProfile(wxUserProfileFormDTO);
        return ResponseUtil.success();
    }


    /**
     * 获取手机号登录，返回token
     * @param dto
     * @return
     */
    @RequestMapping("/token")
    public ResponseResult login(@RequestBody @Validated WxResolvePhoneFormDTO dto){
        try {
            String token = appAuthService.appLogin(dto);
            return ResponseUtil.success(token);
        }catch (Exception e){
            return ResponseUtil.fail(ResponseCodeEnum.FAILD);
        }
    }

    @RequestMapping("/test_token")
    public ResponseResult testLogin(String phone){
        try {
            String token = appAuthService.testLogin(phone);
            return ResponseUtil.success(token);
        }catch (Exception e){
            return ResponseUtil.fail(ResponseCodeEnum.FAILD);
        }
    }

}
