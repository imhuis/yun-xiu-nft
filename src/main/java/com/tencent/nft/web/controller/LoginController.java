package com.tencent.nft.web.controller;

import cn.hutool.Hutool;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import com.tencent.nft.common.constant.SecurityConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/public/captcha.png",method = RequestMethod.GET)
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        request.getSession().setAttribute(SecurityConstants.SESSION_KEY_IMAGE_CODE, captcha.getCode());
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
    }

    @RequestMapping("/public/forget_pwd")
    public ResponseResult forgetPassword(){
        return ResponseUtil.success("开发中，敬请期待！");
    }

    @RequestMapping("/public/star")
    public void star(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final ServletOutputStream outputStream = response.getOutputStream();
        try {
            outputStream.println("感谢各项目成员在此做出的贡献！！！");
        }finally {
            outputStream.flush();
            outputStream.close();
        }
//        ImageIO.write(captcha.getImage(), "png", outputStream);
    }

}
