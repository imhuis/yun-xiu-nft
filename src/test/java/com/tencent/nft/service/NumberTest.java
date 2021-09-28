package com.tencent.nft.service;

import com.tencent.nft.common.util.MoneyUtil;

import java.math.BigDecimal;

/**
 * @author: imhuis
 * @date: 2021/9/29
 * @description:
 */
public class NumberTest {

    public static void main(String[] args) {
        System.out.println(MoneyUtil.yuan2fen(new BigDecimal("0.01")));
    }
}
