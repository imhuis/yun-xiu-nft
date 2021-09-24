package com.tencent.nft.web.controller.nft;

import com.tencent.nft.common.base.PageBean;
import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.common.enums.ICommonEnum;
import com.tencent.nft.common.enums.NFTStatusEnum;
import com.tencent.nft.common.enums.NFTTypeEnum;
import com.tencent.nft.common.enums.ResponseCodeEnum;
import com.tencent.nft.common.exception.RecordNotFoundException;
import com.tencent.nft.common.util.UUIDUtil;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.NFTProduct;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.*;
import com.tencent.nft.service.INftManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
@RestController
@RequestMapping("/admin/nft")
public class NftManagementController {

    @Autowired
    private INftManagementService nftManagementService;

    /**
     * 父NFT列表展示
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseResult nftList(@RequestParam(value = "page",required = false, defaultValue = "1") Integer page,
                                  @RequestParam(value = "per_page",required = false, defaultValue = "20") Integer size,
                                  @RequestParam(value = "nft_status",required = false) Integer nftStatus,
                                  @RequestBody(required = false) NftListQueryDTO nftListQueryDTO){

        if (nftListQueryDTO == null){
            nftListQueryDTO = new NftListQueryDTO();
        }
        PageBean nftListVOList = nftManagementService.listNFT(page, size, nftStatus, nftListQueryDTO);
        return ResponseUtil.success(nftListVOList);
    }

    /**
     * 获取NFT详情
     * @return
     */
    @RequestMapping(value = "/{nftId}", method = RequestMethod.GET)
    public ResponseResult nftDetail(@PathVariable("nftId") String nftId){
        SuperNFT nftDetailsVO;
        try {
            nftDetailsVO = nftManagementService.nftDetail(nftId);
        }catch (RecordNotFoundException e){
            return ResponseUtil.fail(ResponseCodeEnum.NFT_4001);
        }
        return ResponseUtil.success(nftDetailsVO);
    }

    /**
     * 获取海报详情
     * @param nftId
     * @return
     */
    @RequestMapping(value = "/{nftId}", method = RequestMethod.GET, params = {
            "detail=true"
    })
    public ResponseResult nftDetails(@PathVariable("nftId") String nftId){
//        nftManagementService.getPosterPic(nftId);
        return ResponseUtil.success(nftManagementService.getPosterPic(nftId));
    }

    @RequestMapping(value = "/sub/{superNFT}", method = RequestMethod.GET)
    public ResponseResult subNftList(@RequestParam(value = "page",required = false, defaultValue = "1") Integer page,
                                     @RequestParam(value = "per_page",required = false, defaultValue = "20") Integer size,
                                     @PathVariable("superNFT") String superNFTId,
                                     @RequestBody(required = false) SubNFTQueryDTO subNFTQueryDTO){

        if (subNFTQueryDTO == null){
            subNFTQueryDTO = new SubNFTQueryDTO();
        }
        PageBean nftListVOList = nftManagementService.listSubNFT(page, size, superNFTId, subNFTQueryDTO);
        return ResponseUtil.success(nftListVOList);
    }

    /**
     * 创建新的nft
     * @param dto
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseResult createNFT(@RequestBody @Validated NftCreateDTO dto, BindingResult bindingResult){
        // 必要的参数检查
        //        checkParam();

        if (dto.getDetailPicture().size() > 6){
            return ResponseUtil.fail(ResponseCodeEnum.NFT_4003);
        }
        NFTInfo nftInfo = new NFTInfo();
        // Nft id生成规则 - 后面确认
        nftInfo.setNftId(UUIDUtil.generateUUID());
        nftInfo.setNftName(dto.getNftName());
        nftInfo.setNftType(ICommonEnum.getEnum(dto.getNftType(), NFTTypeEnum.class));
        nftInfo.setNftStatus(NFTStatusEnum.WAITING);
        nftInfo.setNftFile(dto.getNftFile());
        nftInfo.setIssuer(dto.getIssuer());
        nftInfo.setIntroduce(dto.getIntroduce());
        nftInfo.setNftCreateTime(LocalDateTime.now());
        nftInfo.setCoverPicture(dto.getCoverPicture());
        nftInfo.setDetailPicture(dto.getDetailPicture().stream().collect(Collectors.joining(",")));

        try {
            nftManagementService.createNFT(nftInfo);
        }catch (Exception e){
            return ResponseUtil.fail(ResponseCodeEnum.NFT_4002);
        }
        return ResponseUtil.success(nftInfo);
    }

    @RequestMapping(value = "/delete.action", method = RequestMethod.POST)
    public ResponseResult deleteNFT(@RequestBody @Validated NftDeleteDTO nftDeleteDTO, BindingResult result){
        if (result.hasFieldErrors()){
            return ResponseUtil.fail(ResponseCodeEnum.FAILD);
        }
        nftManagementService.deleteNft(nftDeleteDTO.getNftId());
        return ResponseUtil.success();
    }

    /**
     * 预售设置，相当于发布商品
     * @return
     */
    @RequestMapping(value = "/pre_sale", method = RequestMethod.POST)
    public ResponseResult setSale(@RequestBody @Validated PreSaleDTO n, BindingResult result){
        if (result.hasFieldErrors()){
            return ResponseUtil.fail(ResponseCodeEnum.FAILD);
        }
        if (!n.getReserveEndTime().isAfter(n.getReserveStartTime())){
            return ResponseUtil.fail(ResponseCodeEnum.YS_5001);
        }
        if (!n.getSellEndTime().isAfter(n.getSellStartTime())){
            return ResponseUtil.fail(ResponseCodeEnum.YS_5002);
        }
        if (!n.getSellStartTime().isAfter(n.getReserveEndTime())){
            return ResponseUtil.fail(ResponseCodeEnum.YS_5003);
        }
        try {
            nftManagementService.setPreSale(n);
        }catch (RecordNotFoundException e){
            return ResponseUtil.fail(ResponseCodeEnum.NFT_4001);
        }

        return ResponseUtil.success();
    }

    /**
     * 商品下架
     * @return
     */
    @RequestMapping(value = "/off_shelf", method = RequestMethod.POST)
    public ResponseResult offShelf(){
        return ResponseUtil.success();
    }

    private ResponseResult checkYSINfo(NFTInfo n) {
        return null;
    }

}
