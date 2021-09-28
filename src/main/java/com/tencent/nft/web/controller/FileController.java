package com.tencent.nft.web.controller;


import com.tencent.nft.entity.nft.vo.SysResult;
import com.tencent.nft.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {
    @Autowired
    private IFileService IFileService;

    @PostMapping("/upload")
    public SysResult upload(@RequestParam("file") MultipartFile file)  {

        return IFileService.upload(file);
    }


}
