package com.tencent.nft.web.controller.app;

import com.tencent.nft.common.base.FeedBack;
import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.common.enums.ResponseCodeEnum;
import com.tencent.nft.core.security.SecurityUtils;
import com.tencent.nft.entity.app.vo.CollectionVO;
import com.tencent.nft.entity.nft.vo.MyLibraryVO;
import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.service.IFeedService;
import com.tencent.nft.service.IAppAuthService;
import com.tencent.nft.service.IAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/23
 * @description: 小程序界面接口
 */
@RestController
@RequestMapping("/app")
public class AppController {

    final Logger log = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private IAppAuthService appAuthService;

    @Autowired
    private IAppService appService;

    @Autowired
    private IFeedService feedService;

    /**
     * 个人信息
     * @return
     */
    @RequestMapping("/me")
    public ResponseResult my(){
        String p = SecurityUtils.getCurrentUsername().get();
        if (p == null){
            return ResponseUtil.fail(ResponseCodeEnum.Record_NoFound);
        }
        WxUser wxUser = appAuthService.getMyInformation(p);
        return ResponseUtil.success(wxUser);
    }

    /**
     * 藏品
     * @return
     */
    @RequestMapping("/me/library")
    public ResponseResult myLibrary(){
        List<MyLibraryVO> collectionVOList = appService.myLibrary();
        return ResponseUtil.success(collectionVOList);
    }

    /**
     * 藏品详情
     * @return
     */
    @RequestMapping("/me/library/{subId}")
    public ResponseResult myLibrary(@PathVariable String subId){
        CollectionVO collectionVO = appService.myCollectionDetail(subId);
        return ResponseUtil.success(collectionVO);
    }

    /**
     * 接收前端客户的反馈信息
     * 插入数据库
     */
    @PostMapping("/feedback")
    public ResponseResult insert(@RequestBody @Validated FeedBack feedBack, BindingResult result) {
        if (result.hasFieldErrors()){
            return ResponseUtil.fail(ResponseCodeEnum.Validation_Error, result.getFieldErrors().get(0));
        }
        feedService.insert(feedBack);
        return ResponseUtil.success();
    }

}
