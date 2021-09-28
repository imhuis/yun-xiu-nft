package com.tencent.nft.entity.nft.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ImageVO {

    //虚拟路径 动态变化的路径
    private String virtualPath;

    // 网络地址
    private String urlPath;

    // 图片名称
    private String fileName;
}
