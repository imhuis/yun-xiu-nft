package com.tencent.nft.common.enums;

/**
 * @author: zyixh
 * @date: 2020/1/28
 * @description:
 */
public enum ResponseCodeEnum {

    SUCCESS(0, "success"),
    FAILD(-1, "fail"),

    PARAMETER_MISSING(1001, "没有请求参数"),
    REQUEST_BODY_MISSING(1002, "请求体缺失"),
    Record_NoFound(1003, "未查询到该条记录"),
    Validation_Error(1004, "参数校验异常"),

    NFT_4001(4001, "记录不存在"),
    NFT_4002(4002, "创建失败"),
    NFT_4003(4003, "详情最多上传6张图片"),
    NFT_4004(4004, ""),
    YS_5001(5001, "预售开始时间晚于预售结束时间"),
    YS_5002(5002, "开售时间晚于开售结束时间"),
    YS_5003(5003, "预售结束时间晚于开售时间"),
    YS_5004(5004, "预售或售卖开始时间早于当前时间"),
    YY_6000(6000, "重复预约");

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
