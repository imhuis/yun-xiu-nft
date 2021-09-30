package com.tencent.nft.web.controller.pay;

import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.common.enums.ResponseCodeEnum;
import com.tencent.nft.common.exception.business.PayException;
import com.tencent.nft.entity.pay.bo.PrepayVO;
import com.tencent.nft.entity.pay.dto.PayRequestDTO;
import com.tencent.nft.service.IPayService;
import com.tencent.nft.web.controller.app.AppController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description:
 */
@RestController
public class PayController {

    final Logger log = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private IPayService payService;

    /**
     * 预支付接口，生成预订单，注意超卖
     * @return
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResponseResult payTransactions(@RequestBody @Validated PayRequestDTO payRequestDTO, BindingResult result) throws Exception {
        if (result.hasFieldErrors()){
            // 参数校验失败
            return ResponseUtil.fail(ResponseCodeEnum.Validation_Error, result.getAllErrors().get(0));
        }
        log.debug("api-/pay: requestBody: \n{}", payRequestDTO.toString());
        try {
            PrepayVO prepayVO = payService.prePay(payRequestDTO);
            return ResponseUtil.success(prepayVO);
        } catch (PayException e){
            return ResponseUtil.define(7001, e.getMessage());
        } catch (Exception e){
            return ResponseUtil.fail(ResponseCodeEnum.FAILD);
        }
    }

}
