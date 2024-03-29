package com.tencent.nft.common.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: imhuis
 * @date: 2021/9/24
 * @description:
 */
public class BusinessIdGenerate {

    public static String generateSuperNftId(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        StringBuilder sb = new StringBuilder("YX");
        sb.append(dtf.format(LocalDateTime.now()));
        return sb.toString();
    }

    public static String toLowerCase(String txt){
        return StringUtils.trim(txt.toLowerCase());
    }


}
