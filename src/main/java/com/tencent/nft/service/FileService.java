package com.tencent.nft.service;


import com.tencent.nft.entity.nft.vo.SysResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

   SysResult upload(MultipartFile file);

}
