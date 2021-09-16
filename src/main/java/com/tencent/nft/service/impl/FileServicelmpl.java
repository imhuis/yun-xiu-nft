package com.tencent.nft.service.impl;



import com.tencent.nft.entity.nft.vo.SysResult;
import com.tencent.nft.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;


@Service
public class FileServicelmpl implements FileService {



    @Override
    public SysResult upload(MultipartFile file) {
        if(file.isEmpty()){
            return SysResult.fail("文件为空!");
        }
        String OriginalFilename = file.getOriginalFilename();
        String fileName = System.currentTimeMillis()+"."+OriginalFilename.substring(OriginalFilename.lastIndexOf(".")+1);
        String filePath = "F:\\images\\";
        File dest = new File(filePath+fileName);
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
