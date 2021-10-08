package com.tencent.nft.web.controller.admin;

import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description: 订单管理，管理界面
 */
@RestController
public class OrderMgrController {

    // 订单列表
    public ResponseResult orderList(){

        return ResponseUtil.success();
    }

}
