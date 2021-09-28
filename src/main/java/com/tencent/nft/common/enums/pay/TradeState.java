package com.tencent.nft.common.enums.pay;

import com.tencent.nft.common.enums.ICommonEnum;

/**
 * @author: imhuis
 * @date: 2021/9/26
 * @description:
 */
public enum TradeState implements ICommonEnum {

    SUCCESS(1, "支付成功"),
    REFUND(-1, "转入退款"),
    NOTPAY(-2, "未支付"),
    CLOSED(-3, "已关闭"),
    REVOKED(-4, "已撤销"),
    USERPAYING(-5, "用户支付中"),
    PAYERROR(-6, "支付失败");

    final int code;
    final String value;

    TradeState(int code, String value) {
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
