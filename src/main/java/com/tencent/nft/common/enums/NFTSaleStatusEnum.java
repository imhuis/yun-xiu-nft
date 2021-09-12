package com.tencent.nft.common.enums;

/**
 * @author: imhuis
 * @date: 2021/9/9
 * @description: NFT出售状态
 */
public enum NFTSaleStatusEnum implements ICommonEnum {

    NotSold(0, "未出售"),
    Sold(1, "已出售"),
    OFFShelf(3, "已下架");

    final int code;
    final String value;

    NFTSaleStatusEnum(int code, String value) {
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
