package com.tencent.nft.web.controller;


import com.tencent.nft.entity.nft.vo.ImageVO;
import com.tencent.nft.entity.nft.vo.SysResult;
import com.tencent.nft.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public SysResult upload(MultipartFile file) throws IOException {

        ImageVO imageVO = fileService.upload(file);
        //不成功 应该返回null
        if(imageVO == null){
            return SysResult.fail();
        }
        return SysResult.success(imageVO);
    }


}
