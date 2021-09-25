package com.tencent.nft.common.util.otpcode;

/**
 * Created by feng on 2016/6/15.
 */
public class OtpCodeException extends Exception {

    private static final long serialVersionUID = -6475559837343698189L;

    public OtpCodeException() {
        super();
    }

    public OtpCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public OtpCodeException(String message) {
        super(message);
    }

    public OtpCodeException(Throwable cause) {
        super(cause);
    }
}
