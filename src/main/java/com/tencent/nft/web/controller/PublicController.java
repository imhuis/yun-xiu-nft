package com.tencent.nft.web.controller;

import com.tencent.nft.entity.pay.WxNotifyResult;
import com.tencent.nft.service.IPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author: imhuis
 * @date: 2021/9/26
 * @description:
 */
@RestController
public class PublicController {

    final Logger logger = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private IPayService payService;


    /**
     * 微信支付成功回调接口
     * @return
     */
    @RequestMapping(value = "/public/notify/wxpay/pay.yy", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<WxNotifyResult> notifyAction(HttpServletRequest request){
        String resJson = "";
        try {
            InputStream inputStream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resJson = sb.toString();
            logger.info(resJson);
            payService.notifyApp(resJson);
            return ResponseEntity.ok(new WxNotifyResult("SUCCESS","成功"));
        } catch (Exception e) {
            System.out.println("微信手机支付失败:" + e.getMessage());
//            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new WxNotifyResult("FAIL",""));
        }
    }
}
