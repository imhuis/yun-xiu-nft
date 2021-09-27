package com.tencent.nft.common.util;

import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class WxPayUtil {

    static final Logger logger = LoggerFactory.getLogger(WxPayUtil.class);

    /**
     * 微信证书别名
     */
    private static final String KEY_ALIAS = "Tenpay Certificate";
    /**
     * 微信数据解密方式
     */
    private static final String ALGORITHM = "AES";
    /**
     * 微信数据解密方式V2
     */
    private static final String ALGORITHM_MODE_PADDING_V2 = "AES/ECB/PKCS7Padding";
    /**
     * 微信数据解密方式V3
     */
    private static final String ALGORITHM_MODE_PADDING_V3 = "AES/GCM/NoPadding";
    /**
     * APIV3Key长度
     */
    private static final int APIV3_KEY_LENGTH_BYTE = 32;
    /**
     * 身份验证标记长度
     */
    private static final int TAG_LENGTH_BIT = 128;


    /**
     * 获取商户私钥
     *
     * @param keyPath 商户私钥证书路径
     * @return 商户私钥
     * @throws Exception 解析 key 异常
     */
    public static String getPrivateKey(String keyPath) throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("pay/apiclient_key.pem");
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
                new FileInputStream(classPathResource.getFile()));

        return "";
    }


    public static String getPaySign(String msg, PrivateKey privateKey){
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(privateKey);
            sign.update(msg.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(sign.sign());
        }catch (Exception e){
            throw new RuntimeException();
        }

//        catch (NoSuchAlgorithmException e) {
//            logger.error("paySign", "微信支付 - 支付签名失败", "当前Java环境不支持SHA256withRSA");
//            throw new WechatPayRuntimeException("当前Java环境不支持SHA256withRSA", e);
//        } catch (SignatureException e) {
//            logger.error("paySign", "微信支付 - 支付签名失败", "签名计算失败");
//            throw new WechatPayRuntimeException("签名计算失败", e);
//        } catch (InvalidKeyException e) {
//            logger.error("paySign", "微信支付 - 支付签名失败", "无效的私钥");
//            throw new WechatPayRuntimeException("无效的私钥", e);
//        } catch (SignatureException e) {
//            e.printStackTrace();
//        }
    }


    /**
     * 解密回调数据V3版
     *
     * @param associatedData 附加数据
     * @param nonce          随机字符串
     * @param ciphertext     数据密文
     * @param apiV3Key       V3秘钥
     * @return 解密后的JSON数据
     */
    public static String decryptNotifyV3(String associatedData, String nonce, String ciphertext, String apiV3Key) {
        byte[] nonceBytes = nonce.getBytes(StandardCharsets.UTF_8);
        byte[] apiV3KeyBytes = apiV3Key.getBytes(StandardCharsets.UTF_8);
        if (apiV3KeyBytes.length != APIV3_KEY_LENGTH_BYTE) {
            throw new IllegalArgumentException("无效的ApiV3Key，长度必须为32个字节");
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING_V3);
            SecretKeySpec key = new SecretKeySpec(apiV3KeyBytes, ALGORITHM);
            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BIT, nonceBytes);
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            cipher.updateAAD(associatedData.getBytes());
            return new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            logger.error("decryptNotifyV3", "微信支付 - 解密V3回调数据失败", e.getMessage());
            throw new IllegalStateException(e);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
            logger.error("decryptNotifyV3", "微信支付 - 解密V3回调数据失败", e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }

}
