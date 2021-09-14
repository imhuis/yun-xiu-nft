package com.tencent.nft.entity.nft.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ImageVO {
    private String virtualPath; //虚拟路径 动态变化的路径
    private String urlPath;     //网络地址
    private String fileName;    //图片名称
}
