package com.imhui.security.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {


    @RequestMapping(value = "/public/captcha.png",method = RequestMethod.GET)
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
//        captcha.write(response.getOutputStream());
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");

        final ServletOutputStream outputStream = response.getOutputStream();
        try {
            ImageIO.write(captcha.getImage(), "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            outputStream.flush();
            outputStream.close();
        }

//        captcha.verify("1234");
    }
}
