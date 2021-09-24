package com.tencent.nft.web.controller.nft;

import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.service.INftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping("/app/market")
    public ResponseResult market(@RequestParam(value = "status", defaultValue = "0") Integer status){
        List<NFTInfo> marketList = nftService.getMarketList(status);
        return ResponseUtil.success(marketList);
    }

    @RequestMapping("/app/market/{nft_id}")
    public ResponseResult productDetail(@PathVariable(value = "nft_id") String nftId){
        return ResponseUtil.success(nftId);
    }

}
