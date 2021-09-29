package com.tencent.nft.common.exception.business;

import com.tencent.nft.common.exception.CustomizeException;

/**
 * @author: imhuis
 * @date: 2021/9/12
 * @description:
 */
public class RecordNotFoundException extends CustomizeException {

    public RecordNotFoundException() {
        super();
    }

    public RecordNotFoundException(String message) {
        super(message);
    }
}
