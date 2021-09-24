package com.tencent.nft.web.controller.app;

import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.common.enums.ResponseCodeEnum;
import com.tencent.nft.core.security.SecurityUtils;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.service.IAppAuthService;
import com.tencent.nft.service.IAppService;
import com.tencent.nft.service.INftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/23
 * @description:
 */
@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private IAppAuthService appAuthService;

    @Autowired
    private IAppService appService;

    /**
     * 个人信息
     * @return
     */
    @RequestMapping("/me")
    public ResponseResult my(){
        String p = SecurityUtils.getCurrentUsername().get();
        if (p == null){
            return ResponseUtil.fail(ResponseCodeEnum.CC_1003);
        }
        WxUser wxUser = appAuthService.getMyInformation(p);
        return ResponseUtil.success(wxUser);
    }

    @RequestMapping("/me/library")
    public ResponseResult myLibrary(){
        appService.myLibrary();
        return ResponseUtil.success();
    }
}
