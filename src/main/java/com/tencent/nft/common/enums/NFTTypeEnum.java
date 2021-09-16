package com.tencent.nft.common.enums;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description: NFT媒体类型枚举
 */
public enum NFTTypeEnum implements ICommonEnum {

    PICTURE(1, "picture"),
    VIDEO(2, "video"),
    AUDIO(3, "audio");

    final int code;
    final String value;

    NFTTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }
}