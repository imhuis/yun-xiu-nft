package com.tencent.nft.web.controller.app;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.common.enums.ResponseCodeEnum;
import com.tencent.nft.core.security.SecurityUtils;
import com.tencent.nft.entity.app.vo.CollectionVO;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.vo.MyLibraryVO;
import com.tencent.nft.entity.pay.PayRequestDTO;
import com.tencent.nft.entity.pay.bo.PrepayBO;
import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.service.IAppAuthService;
import com.tencent.nft.service.IAppService;
import com.tencent.nft.service.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private IPayService payService;

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
        CollectionVO collectionVO = appService.collectionDetails(subId);
        return ResponseUtil.success(collectionVO);
    }

    /**
     * 预支付接口
     * @return
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResponseResult payTransactions(@RequestBody @Validated PayRequestDTO payRequestDTO){
        System.out.println(payRequestDTO.toString());
        try {
            PrepayBO prepayBO = payService.prePay(payRequestDTO);
            return ResponseUtil.success(prepayBO);
        }catch (Exception e){
            return ResponseUtil.fail(ResponseCodeEnum.FAILD);
        }
    }
}
