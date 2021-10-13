package com.tencent.nft.web.controller.nft;

import com.tencent.nft.common.base.PageBean;
import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.common.enums.ResponseCodeEnum;
import com.tencent.nft.common.exception.business.NftManageException;
import com.tencent.nft.common.exception.business.RecordNotFoundException;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.*;
import com.tencent.nft.service.INftManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description: nft管理后台api
 */
@RestController
@RequestMapping("/admin/nft")
public class NftManagementController {

    final Logger log = LoggerFactory.getLogger(NftManagementController.class);

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
                                  @RequestParam(value = "nft_id",required = false) String nftId,
                                  @RequestParam(value = "nft_name",required = false) String nftName,
                                  @RequestParam(value = "nft_type",required = false) Integer nftType){

        // @RequestBody(required = false) NftListQueryDTO nftListQueryDTO
//        NftListQueryDTO nftListQueryDTO = Optional.ofNullable(nftListQueryDTO).orElse(new NftListQueryDTO());
        NftListQueryDTO nftListQueryDTO = new NftListQueryDTO();
        nftListQueryDTO.setNftStatus(nftStatus);
        nftListQueryDTO.setNftId(nftId);
        nftListQueryDTO.setNftName(nftName);
        nftListQueryDTO.setNftType(nftType);

        PageBean nftListVOList = nftManagementService.listNFT(page, size, nftStatus, nftListQueryDTO);
        return ResponseUtil.success(nftListVOList);
    }

    /**
     * 获取NFT详情
     * @param nftId
     * @return
     */
    @RequestMapping(value = "/{nftId}", method = RequestMethod.GET)
    public ResponseResult nftDetail(@PathVariable("nftId") String nftId){
        SuperNFT nftDetailsVO;
        try {
            nftDetailsVO = nftManagementService.nftDetail(nftId);
        }catch (RecordNotFoundException e){
            return ResponseUtil.fail(ResponseCodeEnum.NFT_4001, e.getMessage());
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
        return ResponseUtil.success(nftManagementService.getPosterPic(nftId));
    }

    /**
     * 查询nft子列表
     * @param page
     * @param size
     * @param superNftId
     * @param subNftId
     * @param saleStatus
     * @return
     */
    @RequestMapping(value = "/sub/{superNFT:[a-zA-Z0-9]{16}}", method = RequestMethod.GET)
    public ResponseResult subNftList(@RequestParam(value = "page",required = false, defaultValue = "1") Integer page,
                                     @RequestParam(value = "per_page",required = false, defaultValue = "20") Integer size,
                                     @PathVariable("superNFT") String superNftId,
                                     @RequestParam(value = "id", required = false) String subNftId,
                                     @RequestParam(value = "status", required = false) Integer saleStatus){

        //@RequestBody(required = false) SubNFTQueryDTO subNFTQueryDTO
//        subNFTQueryDTO = Optional.ofNullable(subNFTQueryDTO).orElse(new SubNFTQueryDTO());
        SubNftQueryDTO subNFTQueryDTO = new SubNftQueryDTO();
        subNFTQueryDTO.setId(subNftId);
        subNFTQueryDTO.setSaleStatus(saleStatus);

        try {
            PageBean nftListVOList = nftManagementService.listSubNFT(page, size, superNftId, subNFTQueryDTO);
            return ResponseUtil.success(nftListVOList);
        }catch (RecordNotFoundException e){
            return ResponseUtil.fail(ResponseCodeEnum.NFT_4001);
        }

    }

    /**
     * 创建新的nft
     * @param dto
     * @param result
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseResult createNFT(@RequestBody @Validated NftCreateDTO dto, BindingResult result){
        if (result.hasFieldErrors()){
            // 参数校验异常
            return ResponseUtil.fail(ResponseCodeEnum.Validation_Error, result.getFieldErrors().get(0));
        }

        if (dto.getDetailPicture().size() > 6){
            return ResponseUtil.fail(ResponseCodeEnum.NFT_4003);
        }

//        try {
            NFTInfo nftInfoResult = nftManagementService.createNFT(dto);
            return ResponseUtil.success(nftInfoResult);
//        }catch (Exception e){
//            return ResponseUtil.fail(ResponseCodeEnum.NFT_4002);
//        }
    }

    /**
     * 删除数字藏品（只能删除状态为待发行中的nft）
     * @param nftDeleteDTO
     * @param result
     * @return
     */
    @RequestMapping(value = "/delete.action", method = RequestMethod.POST)
    public ResponseResult deleteNFT(@RequestBody @Validated NftDeleteDTO nftDeleteDTO, BindingResult result){
        if (result.hasFieldErrors()){
            // 参数校验异常
            return ResponseUtil.fail(ResponseCodeEnum.Validation_Error, result.getFieldErrors().get(0));
        }
        String nftId = nftDeleteDTO.getNftId().trim().toLowerCase();
        nftManagementService.deleteNft(nftId);
        return ResponseUtil.success();
    }

    /**
     * 预售设置（相当于发布商品）
     * @param n
     * @param result
     * @return
     */
    @RequestMapping(value = "/pre_sale", method = RequestMethod.POST)
    public ResponseResult setSale(@RequestBody @Validated PreSaleDTO n, BindingResult result){
        if (result.hasFieldErrors()){
            // 参数校验异常
            return ResponseUtil.fail(ResponseCodeEnum.Validation_Error, result.getFieldErrors().get(0));
        }
//        if (!n.getReserveEndTime().isAfter(n.getReserveStartTime())){
//            return ResponseUtil.fail(ResponseCodeEnum.YS_5001);
//        }
//        if (!n.getSellEndTime().isAfter(n.getSellStartTime())){
//            return ResponseUtil.fail(ResponseCodeEnum.YS_5002);
//        }
        LocalDateTime now = LocalDateTime.now();
//        if (n.getReserveStartTime().isBefore(now) || n.getSellStartTime().isBefore(now)){
//            return ResponseUtil.fail(ResponseCodeEnum.YS_5004);
//        }
//        if (!n.getSellStartTime().isAfter(n.getReserveStartTime())){
//            return ResponseUtil.fail(ResponseCodeEnum.YS_5003);
//        }
        try {
            nftManagementService.setupPreSale(n);
        }catch (RecordNotFoundException e){
            return ResponseUtil.fail(ResponseCodeEnum.NFT_4001);
        }catch (NftManageException e){
            return ResponseUtil.fail(ResponseCodeEnum.NFT_4004, e.getMessage());
        }
        return ResponseUtil.success();
    }

    /**
     * 商品下架
     * @param nftId
     * @return
     */
    @RequestMapping(value = "/off_shelf", method = RequestMethod.POST)
    public ResponseResult offShelf(@RequestParam("nft_id") String nftId){
//        try {
            nftManagementService.offShelf(nftId);
//        }catch (Exception e){
//        }
        return ResponseUtil.success();
    }

}
