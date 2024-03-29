package com.tencent.nft.web.controller.nft;

import cn.hutool.core.util.StrUtil;
import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.common.enums.ResponseCodeEnum;
import com.tencent.nft.common.exception.business.RecordNotFoundException;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.vo.ProductDetailVO;
import com.tencent.nft.entity.nft.vo.ProductVO;
import com.tencent.nft.service.IMarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description: 数字商品市场
 */
@RestController
public class MarketController {

    final Logger log = LoggerFactory.getLogger(MarketController.class);

    @Autowired
    private IMarketService marketService;

    /**
     * nft市场
     * @param status
     * @return
     */
    @RequestMapping({"/app/public/market"})
    public ResponseResult market(@RequestParam(value = "status", defaultValue = "0") Integer status){
        List<NFTInfo> marketList = marketService.getMarketList(status);
        return ResponseUtil.success(marketList);
    }

    /**
     * 商品信息
     * @param nftId
     * @return
     */
    @RequestMapping({"/app/public/market/{nft_id}"})
    public ResponseResult productInfo(@PathVariable(value = "nft_id") String nftId){
        try {
            ProductVO productDetail = marketService.getProductInfo(nftId);
            return ResponseUtil.success(productDetail);
        }catch (RecordNotFoundException e){
            return ResponseUtil.fail(ResponseCodeEnum.Record_NoFound);
        }
    }

    /**
     * 商品详情（封面图、海报）
     * @param nftId
     * @return
     */
    @RequestMapping({"/app/public/market/detail/{nft_id}"})
    public ResponseResult productDetail(@PathVariable(value = "nft_id") String nftId){
        ProductDetailVO vo = null;
        try {
            vo = marketService.getProductDetail(nftId);
        }catch (RecordNotFoundException e){
            ResponseUtil.fail(ResponseCodeEnum.NFT_4001);
        }
        return ResponseUtil.success(vo);
    }

    /**
     * 查询指定商品的预约量和售出量
     * @param nftId
     * @return
     */
    @RequestMapping("/app/public/market/{nft_id:[a-zA-Z0-9]{16}}/amount")
    public ResponseResult getReservationAmount(@PathVariable(value = "nft_id") String nftId){
        long amount = marketService.getProductAmount(nftId);
        return ResponseUtil.success(amount);
    }

    /**
     * 根据子编号指定商品的预约量和售出量
     * @param subNftId
     * @return
     */
    @RequestMapping("/app/public/market/{sub_nft_id:[a-zA-Z0-9]{18,}}/amount")
    public ResponseResult getReservationAmountByZi(@PathVariable(value = "sub_nft_id") String subNftId){
        String superId = StrUtil.sub(subNftId, 0, -4);
        long amount = marketService.getProductAmount(superId);
//        long amount = marketService.getProductAmountBySubNftId(subNftId);
        return ResponseUtil.success(amount);
    }

    /**
     * 对指定商品预约
     * @param nftId
     * @return
     */
    @RequestMapping(value = "/app/market/{nft_id}/reserve", method = RequestMethod.POST)
    public ResponseResult reserve(@PathVariable(value = "nft_id") String nftId){
        try {
            // 预约商品
            boolean flag = marketService.reserveProduct(nftId);
            if (!flag){
                return ResponseUtil.fail(ResponseCodeEnum.YY_6000);
            }

        }catch (Exception e){
            return ResponseUtil.fail(ResponseCodeEnum.FAILD);
        }
        return ResponseUtil.success();
    }

}
