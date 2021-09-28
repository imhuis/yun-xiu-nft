package com.tencent.nft.service.impl;

import com.tencent.nft.entity.nft.vo.SysResult;
import com.tencent.nft.service.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
@Service
public class FileServiceImpl implements IFileService {

    @Override
    public SysResult upload(MultipartFile file) {
        if(file.isEmpty()){
            return SysResult.fail("文件为空!");
        }
        String OriginalFilename = file.getOriginalFilename();
        String fileName = System.currentTimeMillis()+"."+OriginalFilename.substring(OriginalFilename.lastIndexOf(".")+1);
        fileName = fileName.toLowerCase();
        if(!fileName.matches("^.+\\.(jpg|png|gif|svg|avi|wmv|mpeg|quicktime|realvideo|mp4|mp4|wav|cda)$")){
            return SysResult.fail("文件上传格式错误");
        }
        String filePath = "F:\\images\\";
        //准备文件根目录
        String dateDir = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
        String dirPath = filePath + dateDir;
        File dest = new File(dirPath+fileName);
        if(!dest.getParentFile().exists())
            dest.getParentFile().mkdirs();
        try {
            file.transferTo(dest);
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.fail(OriginalFilename+"上传失败！");
        }
        return SysResult.success(fileName);
    }

}
