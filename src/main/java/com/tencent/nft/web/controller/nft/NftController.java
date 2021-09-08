package com.tencent.nft.web.controller.nft;

import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.service.NFTManagementService;
import com.tencent.nft.web.dto.NFTCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller("/admin/nft")
public class NftController {

    @Autowired
    private NFTManagementService nftManagementService;

    /**
     * 管理端展示
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseResult nftList(@RequestParam(value = "page",required = false, defaultValue = "1") Integer page,
                                  @RequestParam(value = "per_page",required = false, defaultValue = "20") Integer size,
                                  @RequestParam(value = "nft_status",required = false) Integer nftStatus){
        nftManagementService.listNFT();
        return ResponseUtil.success();
    }

    /**
     * 获取NFT详情
     * @return
     */
    @RequestMapping(value = "/{nftId}", method = RequestMethod.GET)
    public ResponseResult nftDetail(@PathVariable("nftId") String nftId){
        return ResponseUtil.success();
    }



    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseResult createNFT(@RequestBody @Validated NFTCreateDTO dto){

//        nftManagementService.createNFT();
        return ResponseUtil.success();
    }

}
