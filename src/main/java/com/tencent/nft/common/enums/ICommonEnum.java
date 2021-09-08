package com.tencent.nft.common.enums;

import java.util.EnumSet;
import java.util.Objects;

/**
 * @author: imhuis
 * @date: 2021/9/8
 * @description: https://www.cnblogs.com/legend_sun/p/12098981.html 通用枚举类
 */
public interface ICommonEnum {

    int getCode();

    String getValue();

    static <E extends Enum<E> & ICommonEnum> E getEnum(Integer code, Class<E> clazz) {
        Objects.requireNonNull(code);
        EnumSet<E> all = EnumSet.allOf(clazz);
        return all.stream().filter(e -> e.getCode() == code).findFirst().orElse(null);
    }

    static <E extends Enum<E> & ICommonEnum> E getEnum(String value, Class<E> clazz) {
        Objects.requireNonNull(value);
        EnumSet<E> all = EnumSet.allOf(clazz);
        return all.stream().filter(e -> e.getValue().equals(value)).findFirst().orElse(null);
    }
}
