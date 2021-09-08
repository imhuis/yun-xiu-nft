package com.tencent.nft.web.controller.nft;

import com.tencent.nft.common.base.PageBean;
import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.common.enums.ICommonEnum;
import com.tencent.nft.common.enums.NFTTypeEnum;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.dto.NFTListQueryDTO;
import com.tencent.nft.service.INftManagementService;
import com.tencent.nft.entity.nft.dto.NFTCreateDTO;
import com.tencent.nft.entity.nft.vo.NFTListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
@Controller("/admin/nft")
public class NftManagementController {

    @Autowired
    private INftManagementService INFTManagementService;

    /**
     * 管理端展示
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseResult nftList(@RequestParam(value = "page",required = false, defaultValue = "1") Integer page,
                                  @RequestParam(value = "per_page",required = false, defaultValue = "20") Integer size,
                                  @RequestParam(value = "nft_status",required = false) Integer nftStatus,
                                  @RequestBody NFTListQueryDTO nftListQueryDTO){

        PageBean nftListVOList = INFTManagementService.listNFT(page, size, nftStatus, nftListQueryDTO);
        return ResponseUtil.success(nftListVOList);
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
            INFTManagementService.createNFT(superNFT);
        }catch (Exception e){
            return ResponseUtil.define(-1, "fail");
        }
        return ResponseUtil.success();
    }

}
