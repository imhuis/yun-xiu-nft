/*
 * Copyright © 1998 - 2021 Tencent. All Rights Reserved
 * www.tencent.com
 * All rights reserved.
 */
package com.tencent.nft.common.util;

import java.util.UUID;

/**
 * @author :imhui
 * @date :2021-04-13 10:00
 * @description :
 */
public class UUIDUtil {


    private static IdsRandomStringGenerator randomStringGenerator = new IdsRandomStringGenerator(8);


    private UUIDUtil() {
    }


    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "") + randomStringGenerator.generate();
    }


    /**
     * Always return a random positive number
     */
    public static long generateNumber() {
        long number;
        while (true) {
            number = UUID.randomUUID().getMostSignificantBits();
            if (number > 0) {
                return number;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(generate());
    }

}
