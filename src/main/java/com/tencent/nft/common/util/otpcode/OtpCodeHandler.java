package com.tencent.nft.common.util.otpcode;

import org.apache.commons.codec.binary.Base32;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.SecureRandom;

/**
 * Created by feng on 2016/6/15.
 */
public class OtpCodeHandler implements OtpCodeInterface {

    private static final Logger LOG = LoggerFactory.getLogger(OtpCodeHandler.class);

    private static final int INTERVAL_LENGTH = 30;
    private static final int MAX_DELAY_INTERVALS = 2;
    private static final int CODE_LENGTH = 6;


    @Override
    public String generateCode(String secret) throws OtpCodeException {
        byte[] secretBytes = new Base32().decode(secret);
        long longCode = getCode(secretBytes, getTimeIndex());
        String code = Long.toString(longCode);
        while (code.length() < CODE_LENGTH) {
            code = "0" + code;
        }
        return code;
    }

    @Override
    public boolean checkCode(String secret, String numberCode) throws OtpCodeException {
        byte[] secretBytes = new Base32().decode(secret);
        long timeIndex = getTimeIndex();
        long testCode;
        try {
            testCode = Long.parseLong(numberCode);
        } catch (Exception e) {
            return false;
        }
        // Compare specified code to current code - allow delay of up to 2
        // intervals
        for (int i = -MAX_DELAY_INTERVALS; i <= MAX_DELAY_INTERVALS; i++) {
            long currentCode = getCode(secretBytes, timeIndex - i);
            if (testCode == currentCode) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String generateSecret() {
        byte[] buffer = new byte[10];
        new SecureRandom().nextBytes(buffer);
        return new String(new Base32().encode(buffer));
    }

    //
    // ----------------- Copied implementation --------------------
    //

    /**
     * Get the time index based on current system time and 30 second intervals.
     *
     * @return time index
     */
    protected long getTimeIndex() {
        return System.currentTimeMillis() / 1000 / INTERVAL_LENGTH;
    }

    /**
     * Compute the TOTP code based on the specified secret and time index.
     *
     * @param secret    must be a 16 byte base32 encoded random string
     * @param timeIndex the number of 30s intervals since the UNIX time epoch
     * @return TOTP code
     * @throws OtpCodeException
     */
    private long getCode(byte[] secret, long timeIndex) throws OtpCodeException {
        try {
            SecretKeySpec signKey = new SecretKeySpec(secret, "HmacSHA1");
            ByteBuffer buffer = ByteBuffer.allocate(8);
            buffer.putLong(timeIndex);
            byte[] timeBytes = buffer.array();
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signKey);
            byte[] hash = mac.doFinal(timeBytes);
            int offset = hash[19] & 0xf;
            long truncatedHash = hash[offset] & 0x7f;
            for (int i = 1; i < 4; i++) {
                truncatedHash <<= 8;
                truncatedHash |= hash[offset + i] & 0xff;
            }
            return (truncatedHash %= 1000000);
        } catch (Exception e) {
            LOG.error("Problem generating TOTP code!", e);
            throw new OtpCodeException("Problem generating TOTP code!", e);
        }
    }

    //
    // ----------- Testing - generate new secret, and then a new code every 30
    // seconds for long running comparison with Google Authenticator App. TODO
    // JUnit?
    //
}
