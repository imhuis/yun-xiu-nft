package com.tencent.nft.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 编码工具
 *
 * @author zilong
 * @date 2020/10/30
 */
public class CodeUtil {
    /**
     * 按字典排序签名
     *
     * @param paramMap 字典
     * @param secretKey
     * @return 
     */
    public static String createSignature(Map<String, String> paramMap, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        Set<String> paramKeys = paramMap.keySet();
        List<String> paramKeyList = new ArrayList<>(paramKeys);
        Collections.sort(paramKeyList);

        StringBuilder appendStr = new StringBuilder();
        for (String single : paramKeyList) {
            String value = paramMap.get(single);
            String encodeParam;
            try {
                encodeParam = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                encodeParam = value;
            }
            appendStr.append(single).append("=").append(encodeParam).append("&");
        }
        String signatureParams = appendStr.toString();
        String finalSignatureParams = signatureParams.substring(0, signatureParams.length() - 1);

        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA384");
        Mac mac = Mac.getInstance("HmacSHA384");
        mac.init(key);
        byte[] signatures = mac.doFinal(finalSignatureParams.getBytes(Charset.defaultCharset()));
        return Base64.encodeBase64URLSafeString(signatures);
    }
}
