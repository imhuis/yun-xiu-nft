package com.tencent.nft.web.controller.nft;

import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.common.enums.ResponseCodeEnum;
import com.tencent.nft.common.exception.RecordNotFoundException;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.vo.ProductVO;
import com.tencent.nft.service.INftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description:
 */
@RestController
public class NftController {

    @Autowired
    private INftService nftService;

    /**
     * nft市场
     * @param status
     * @return
     */
    @RequestMapping({"/app/public/market"})
    public ResponseResult market(@RequestParam(value = "status", defaultValue = "0") Integer status){
        List<NFTInfo> marketList = nftService.getMarketList(status);
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
            ProductVO productDetail = nftService.getProductDetail(nftId);
            return ResponseUtil.success(productDetail);
        }catch (RecordNotFoundException e){
            return ResponseUtil.fail(ResponseCodeEnum.CC_1003);
        }
    }


    /**
     * 商品详情
     * @param nftId
     * @return
     */
    @RequestMapping({"/app/public/market/detail/{nft_id}"})
    public ResponseResult productDetail(@PathVariable(value = "nft_id") String nftId){
        return ResponseUtil.success();
    }

    /**
     * 查询指定商品预约量
     * @param nftId
     * @return
     */
    @RequestMapping("/app/public/market/{nft_id}/reservation_amount")
    public ResponseResult getReservationAmount(@PathVariable(value = "nft_id") String nftId){
        long amount = nftService.getReservationAmount(nftId);
        return ResponseUtil.success(amount);
    }


    /**
     * 对指定商品预约
     * @param nftId
     * @return
     */
    @RequestMapping(value = "/app/public/market/{nft_id}/reserve", method = RequestMethod.GET)
    public ResponseResult reserve(@PathVariable(value = "nft_id") String nftId){
        try {
            // 预约商品
            nftService.reserveProduct(nftId);
            return ResponseUtil.success();
        }catch (Exception e){
            return ResponseUtil.success();
        }
    }

}
