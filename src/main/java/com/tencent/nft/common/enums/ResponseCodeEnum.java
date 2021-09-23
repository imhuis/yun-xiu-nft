package com.tencent.nft.common.enums;

/**
 * @author: zyixh
 * @date: 2020/1/28
 * @description:
 */
public enum ResponseCodeEnum {

    SUCCESS(0, "success"),
    FAILD(-1, "fail"),

    METHOD_NOT_ALLOWED(405, "spring.exception.405"),
    PARAMETER_MISSING(1001, "spring.exception.1001"),
    REQUEST_BODY_MISSING(1002, "spring.exception.1002"),

    CC_1001(1001, ""),
    CC_1002(1002, ""),
    CC_1003(1003, "未查询到该条记录"),

    NFT_4001(4001, "记录不存在"),
    NFT_4002(4002, "创建失败"),
    NFT_4003(4003, "详情最多上传6张图片"),
    NFT_4004(4004, ""),
    YS_5001(5001, "预售开始时间晚于预售结束时间"),
    YS_5002(5002, "开售时间晚于开售结束时间"),
    YS_5003(5003, "预售结束时间晚于开售时间");

    private final Integer code;
    private final String message;

    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return code;
    }

    public String message() {
        return message;
    }
}
