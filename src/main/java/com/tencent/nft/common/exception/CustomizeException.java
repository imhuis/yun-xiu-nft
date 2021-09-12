package com.tencent.nft.common.exception;

/**
 * @author: zyixh
 * @date:   2020/1/27
 * @description:
 */
public class CustomizeException extends RuntimeException {

    public CustomizeException() {
    }

    public CustomizeException(String message) {
        super(message);
    }
}
