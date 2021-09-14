package com.tencent.nft.service;


import com.tencent.nft.entity.nft.vo.ImageVO;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    ImageVO upload(MultipartFile file);

}
