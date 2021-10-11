package com.tencent.nft.common.util;

import cn.hutool.core.lang.UUID;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
public class TokenUtil {


    public static String generateToken(){
//        ThreadLocalRandom random = ThreadLocalRandom.current();
//        UUID uuid = new UUID(random.nextInt(), random.nextInt());
//        return uuid.toString();
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
