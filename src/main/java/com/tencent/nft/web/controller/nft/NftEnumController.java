package com.tencent.nft.web.controller.nft;

import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.common.enums.NFTSaleStatusEnum;
import com.tencent.nft.common.enums.NFTStatusEnum;
import com.tencent.nft.common.enums.NFTTypeEnum;
import com.tencent.nft.entity.nft.vo.BoxLabelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: imhuis
 * @date: 2021/9/18
 * @description: 类型、状态枚举属性
 */
@RestController
@RequestMapping("/admin/nft/enum")
public class NftEnumController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * nft数字藏品类型枚举
     * @return
     */
    @RequestMapping("/nft_type")
    public ResponseResult getNftType(){
        Set<BoxLabelVO> boxLabelVOSet = Arrays.asList(NFTTypeEnum.values())
                .stream()
                .map(x -> new BoxLabelVO(x.getCode(), x.getValue()))
                .collect(Collectors.toSet());
        return ResponseUtil.success(boxLabelVOSet);
    }

    /**
     * nft数字藏品状态枚举
     * @return
     */
    @RequestMapping("/nft_status")
    public ResponseResult getNftStatus(){
        Set<BoxLabelVO> boxLabelVOSet = Arrays.asList(NFTStatusEnum.values())
                .stream()
                .map(x -> new BoxLabelVO(x.getCode(), x.getValue()))
                .collect(Collectors.toSet());
        return ResponseUtil.success(boxLabelVOSet);
    }

    /**
     * 子nft状态枚举
     * @return
     */
    @RequestMapping("/nft_sale_status")
    public ResponseResult getSubNftType(){
        Set<BoxLabelVO> boxLabelVOSet = Arrays.asList(NFTSaleStatusEnum.values())
                .stream()
                .map(x -> new BoxLabelVO(x.getCode(), x.getValue()))
                .collect(Collectors.toSet());
        return ResponseUtil.success(boxLabelVOSet);
    }

}
