package com.tencent.nft.common.enums;

public enum NFTTypeEnum {

    PICTURE(1, "picture"),
    VIDEO(2, "video"),
    AUDIO(3, "audio");


    final int code;
    final String type;

    NFTTypeEnum(int code, String type) {
        this.code = code;
        this.type = type;
    }
}
