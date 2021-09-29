package com.tencent.nft.common.exception.business;

import com.tencent.nft.common.exception.CustomizeException;

/**
 * @author: imhuis
 * @date: 2021/9/29
 * @description:
 */
public class NftManageException extends CustomizeException {

    public NftManageException() {
    }

    public NftManageException(String message) {
        super(message);
    }
}
