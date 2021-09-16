package com.tencent.nft.web.controller;


import com.tencent.nft.entity.nft.vo.ImageVO;
import com.tencent.nft.entity.nft.vo.SysResult;
import com.tencent.nft.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public SysResult upload(@RequestParam("file") MultipartFile file)  {

        return fileService.upload(file);
    }


}
