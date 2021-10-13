package com.tencent.nft.web.controller.admin;

import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.entity.pay.dto.OrderQueryDTO;
import com.tencent.nft.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description: 订单管理，管理界面
 */
@RestController
@RequestMapping("/admin")
public class OrderMgrController {

    @Autowired
    private IOrderService orderService;

    /**
     * 订单列表
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ResponseResult orderList(@RequestParam(value = "page",required = false, defaultValue = "1") Integer page,
                                    @RequestParam(value = "per_page",required = false, defaultValue = "20") Integer size,
                                    @RequestParam(value = "nft_id",required = false) String nftId,
                                    @RequestParam(value = "nft_name",required = false) String nftName,
                                    @RequestParam(value = "phone",required = false) String phone,
                                    @RequestParam(value = "date",required = false) LocalDate soldDate){

        orderService.getOrderList(page, size, new OrderQueryDTO());
        return ResponseUtil.success();
    }

}
