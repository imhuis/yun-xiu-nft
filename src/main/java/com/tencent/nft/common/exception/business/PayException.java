package com.tencent.nft.common.exception.business;

import com.tencent.nft.common.exception.CustomizeException;

/**
 * @author: imhuis
 * @date: 2021/9/30
 * @description:
 */
public class PayException extends CustomizeException {

    public PayException() {
    }

    public PayException(String message) {
        super(message);
    }
}
