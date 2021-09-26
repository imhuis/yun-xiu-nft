package com.tencent.nft.common.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.commons.collections.map.LinkedMap;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.time.Instant;
import java.util.*;

public class SignUtil {

    public static String createSignUrl(String characterEncoding, SortedMap<Object,Object> parameters, String key){
        StringBuffer sb = new StringBuffer();
        StringBuffer sbkey = new StringBuffer();
        Set es = parameters.entrySet();  //所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            //空值不传递，不参与签名组串
            if(null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
                sbkey.append(k + "=" + v + "&");
            }
        }
        //System.out.println("字符串:"+sb.toString());
        sbkey.deleteCharAt(sb.length() - 1);
        sbkey=sbkey.append(key);
        System.out.println("字符串:"+ sbkey);
        //MD5加密,结果转换为大写字符
        String sign = MD5Util.MD5Encode(sbkey.toString(), characterEncoding).toUpperCase();
        System.out.println("MD5加密值:"+sign);
        return sb +"sign="+sign;
    }

    /**
     *
     * @param characterEncoding 指定字符集UTF-8
     * @param parameters 参与签名的参数
     * @param key MD5签名KEY
     * @return
     */
    public static String createSign(String characterEncoding, SortedMap<String,String> parameters, String key){
        StringBuffer sb = new StringBuffer();
        StringBuffer sbkey = new StringBuffer();
        Set es = parameters.entrySet();  //所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            //空值不传递，不参与签名组串
            if(null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
                sbkey.append(k + "=" + v + "&");
            }
        }
        //System.out.println("字符串:"+sb.toString());
        sbkey.deleteCharAt(sb.length() - 1);
        System.out.println("字符串:" + sbkey);
        //MD5加密,结果转换为大写字符
        String sign = MD5Util.MD5Encode(sbkey.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    public static String sign() throws FileNotFoundException {
        List<String> strings = new LinkedList<>();
        strings.add("wxb3982d59b8a5e644" + "\n");
        strings.add(Instant.now().getEpochSecond() + "\n");
        strings.add(RandomUtil.randomString(32) + "\n");
        strings.add("prepay_id=wx201410272009395522657a690389285100" + "\n");
        StringBuilder builder = new StringBuilder();
        for (String string : strings) {
            builder.append(string);
        }

        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new FileInputStream("D:\\data\\pay\\apiclient_key.pem"));
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA);

        sign.setPrivateKey(merchantPrivateKey);

        byte[] signData = sign.sign(builder.toString().getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(signData);
    }

    public static void main(String[] args) throws IOException {

//        String timestamp = String.valueOf(Instant.now().getEpochSecond());
//        String noStr = RandomUtil.randomString(32);
//
//        SortedMap<String,String> parameters = new TreeMap<>();
//        parameters.put("appId", "wxb3982d59b8a5e644");
//        parameters.put("nonceStr", noStr);
//        parameters.put("timeStamp", timestamp);
//        parameters.put("package", "prepay_id=wx201410272009395522657a690389285100");
//
//        System.out.println("parameters " + parameters);
//
//        System.out.println("json " + new ObjectMapper().writeValueAsString(parameters));
//        String sign = createSign("UTF-8", parameters,"da51a11a-4893-44e9-87dc-29b2890ec770");
//        System.out.println("签名：" + sign);

        System.out.println(sign());

    }
}
