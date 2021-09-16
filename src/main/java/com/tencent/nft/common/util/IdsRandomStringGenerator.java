/*
 * Copyright © 1998 - 2021 Tencent. All Rights Reserved
 * www.tencent.com
 * All rights reserved.
 */
package com.tencent.nft.common.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author :bobzbfeng
 * @date :2021-04-13 10:00
 * @description :
 */
public class IdsRandomStringGenerator {


    private static final char[] DEFAULT_CODEC = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            .toCharArray();

    private static final char[] NUMBER_CODE = "0123456789".toCharArray();

    private Random random = new SecureRandom();

    private int length;

    /**
     * Create a generator with the default length (6).
     */
    public IdsRandomStringGenerator() {
        this(6);
    }

    /**
     * Create a generator of random strings of the length provided
     *
     * @param length the length of the strings generated
     */
    public IdsRandomStringGenerator(int length) {
        this.length = length;
    }

    public String generate() {
        byte[] verifierBytes = new byte[length];
        random.nextBytes(verifierBytes);
        return getAuthorizationCodeString(verifierBytes);
    }

    /**
     * Convert these random bytes to a verifier string. The length of the byte array can be
     * {@link #setLength(int) configured}. The default implementation mods the bytes to fit into the
     * ASCII letters 1-9, A-Z, a-z .
     *
     * @param verifierBytes The bytes.
     * @return The string.
     */
    protected String getAuthorizationCodeString(byte[] verifierBytes) {
        char[] chars = new char[verifierBytes.length];
        for (int i = 0; i < verifierBytes.length; i++) {
            chars[i] = DEFAULT_CODEC[((verifierBytes[i] & 0xFF) % DEFAULT_CODEC.length)];
        }
        return new String(chars);
    }

    protected String getNumberCodeString(byte[] verifierBytes) {
        char[] chars = new char[verifierBytes.length];
        for (int i = 0; i < verifierBytes.length; i++) {
            chars[i] = NUMBER_CODE[((verifierBytes[i] & 0xFF) % NUMBER_CODE.length)];
        }
        return new String(chars);
    }

    public String generateNumberCode(){
        byte[] verifierBytes = new byte[length];
        random.nextBytes(verifierBytes);
        return getNumberCodeString(verifierBytes);
    }

    /**
     * The random value generator used to create token secrets.
     *
     * @param random The random value generator used to create token secrets.
     */
    public void setRandom(Random random) {
        this.random = random;
    }

    /**
     * The length of string to generate.
     *
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

}
