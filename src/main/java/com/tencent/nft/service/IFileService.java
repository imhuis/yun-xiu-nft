package com.tencent.nft.service;

import com.tencent.nft.entity.nft.vo.SysResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
public interface IFileService {

   SysResult upload(MultipartFile file);

}
