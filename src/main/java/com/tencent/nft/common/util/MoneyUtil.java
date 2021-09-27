package com.tencent.nft.common.util;

import java.math.BigDecimal;

/**
 * @author: imhuis
 * @date: 2021/9/27
 * @description:
 */
public class MoneyUtil {

    public static Integer yuan2fen(BigDecimal num){
        return num.movePointRight(2).intValue();
    }
}
