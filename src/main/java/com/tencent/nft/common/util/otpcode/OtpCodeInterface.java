package com.tencent.nft.common.util.otpcode;

/**
 * Created by feng on 2016/6/15.
 *
 */
public interface OtpCodeInterface {
    /**
     * Generate the current TOTP code for the specified secret using the configured values.
     *
     * @param secret base32-encoded secret, usually for a particular developer.
     * @return 6 digit otp code
     * @throws OtpCodeException if it cannot generate code.
     */
    public String generateCode(String secret) throws OtpCodeException;

    /**
     * Check the specified code against the current TOTP code for the specified secret. Comparison allows for a configurable delay.
     *
     * @param secret     base32-encoded secret, usually for a particular developer.
     * @param numberCode 6 digit otp code to be checked
     * @return true if code matches within tolerance, else false
     * @throws OtpCodeException if it cannot do the check.
     */
    public boolean checkCode(String secret, String numberCode) throws OtpCodeException;

    /**
     * Generate a random otp secret - 16 character Base32 encoded value.
     *
     * @return secret
     */
    public String generateSecret();
}
