package com.tencent.nft.web.controller;

import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.entity.admin.TemporaryRecord;
import com.tencent.nft.service.ITemporaryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
@RestController
@CrossOrigin
public class TemporaryController {

    @Resource
    private ITemporaryService temporaryService;

    /**
     * 设置临时记录
     *
     * @param temporaryRecord
     * @return
     */
    @PostMapping("/linshiinsert")
    public ResponseResult setupTemporaryRecord(@RequestBody TemporaryRecord temporaryRecord) {
        temporaryService.setupTemporaryRecord(temporaryRecord);
        return ResponseUtil.success();
    }

    /**
     * 根据指定值查询临时记录
     *
     * @return
     */
    @GetMapping("/public/selectlinshi")
    public ResponseResult queryTemporaryRecord() {
        String record = temporaryService.selectByKeyWord("");
        return ResponseUtil.success(record);
    }
}
