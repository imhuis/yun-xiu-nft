package com.tencent.nft.web.controller.nft;

import com.tencent.nft.common.base.PageBean;
import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.common.enums.ICommonEnum;
import com.tencent.nft.common.enums.NFTTypeEnum;
import com.tencent.nft.common.exception.RecordNotFoundException;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NftDeleteDTO;
import com.tencent.nft.entity.nft.dto.NftListQueryDTO;
import com.tencent.nft.entity.nft.dto.SubNFTQueryDTO;
import com.tencent.nft.entity.nft.vo.NFTDetailsVO;
import com.tencent.nft.service.INftManagementService;
import com.tencent.nft.entity.nft.dto.NftCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

        PageBean nftListVOList = nftManagementService.listNFT(page, size, nftStatus, nftListQueryDTO);
        return ResponseUtil.success(nftListVOList);
    }

    @RequestMapping(value = "/sub/{superNFT}", method = RequestMethod.GET)
    public ResponseResult subNftList(@RequestParam(value = "page",required = false, defaultValue = "1") Integer page,
                                  @RequestParam(value = "per_page",required = false, defaultValue = "20") Integer size,
                                  @PathVariable("superNFT") String superNFTId,
                                  @RequestBody(required = false) SubNFTQueryDTO subNFTQueryDTO){

        PageBean nftListVOList = nftManagementService.listSubNFT(page, size, superNFTId, subNFTQueryDTO);
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
            return ResponseUtil.define(0, "查询记录不存在");
        }
        return ResponseUtil.success(nftDetailsVO);
    }



    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseResult createNFT(@RequestBody @Validated NftCreateDTO dto){
        // 参数判断

        if (dto.getDetailPicture().size() > 6){
            return ResponseUtil.define(0, "最多上传6张图片");
        }
        SuperNFT superNFT = new SuperNFT();
        superNFT.setNftName(dto.getNftName());
        superNFT.setNftType(ICommonEnum.getEnum(dto.getNftType(), NFTTypeEnum.class));
        superNFT.setNftFile(dto.getNftFile());
        superNFT.setIssuer(dto.getIssuer());
        superNFT.setIntroduce(dto.getIntroduce());
        superNFT.setCoverPicture(dto.getCoverPicture());
        superNFT.setDetailPicture(dto.getDetailPicture().stream().collect(Collectors.joining(",")));

        try {
            nftManagementService.createNFT(superNFT);
        }catch (Exception e){
            return ResponseUtil.define(-1, "fail");
        }
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/delete.action", method = RequestMethod.POST)
    public ResponseResult deleteNFT(@RequestBody NftDeleteDTO nftDeleteDTO){
        nftManagementService.deleteNft(nftDeleteDTO.getNftId());
        return ResponseUtil.success();
    }

}
