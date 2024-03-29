package com.tencent.nft.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.EnumSet;
import java.util.Objects;

/**
 * @author: imhuis
 * @date: 2021/9/8
 * @description:
 */
public interface ICommonEnum {

    @JsonValue
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
