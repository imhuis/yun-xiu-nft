package com.tencent.nft.common.util;

import com.tencent.nft.common.enums.ICommonEnum;

/**
 * @author: imhuis
 * @date: 2021/9/12
 * @description:
 */
public class CodeEnumUtil {

    public static <E extends Enum<?> & ICommonEnum> E codeOf(Class<E> enumClass, int code) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getCode() == code)
                return e;
        }
        return null;
    }
}
