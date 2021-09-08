package com.tencent.nft.common.enums;

/**
 * @author: imhuis
 * @date: 2021/9/9
 * @description:
 */
public enum NFTStatusEnum implements ICommonEnum {

    WAITING(0, "待发行"),
    PROCESSING(1, "发行中");

    final int code;
    final String value;

    NFTStatusEnum(int code, String value) {
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
