package com.tencent.nft.web.controller;

import cn.hutool.core.io.file.FileNameUtil;
import com.qcloud.cos.model.PutObjectResult;
import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.common.util.FileUploadUtil;
import com.tencent.nft.common.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/public/oss")
public class FileController {

    final Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Value("${cos.endpoint}")
    private String endpoint;

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        String realPath = request.getServletContext().getRealPath("/img");
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        long startTime = System.currentTimeMillis();

        String fileSuffix = FileNameUtil.getSuffix(file.getOriginalFilename());
        String newName = UUIDUtil.generateUUID() + "." + fileSuffix;
        File newFile = new File(folder, newName);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PutObjectResult result = fileUploadUtil.upload(newName, newFile.getAbsolutePath());
        long endTime = System.currentTimeMillis();
        log.info("上传文件耗时：{}ms", endTime - startTime);
        String newFileName = endpoint + newName;
        return ResponseUtil.success(newFileName);
    }

}
