package com.tencent.security.web.controller;

import com.tencent.security.common.base.ResponseResult;
import com.tencent.security.common.base.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
@RestController
public class TestController {


    @RequestMapping("/public/hello")
    public ResponseResult<String> hello(){
        return ResponseUtil.success("Hello World!");
    }

    @RequestMapping("/demo/hello")
    public ResponseResult<String> hi(){
        return ResponseUtil.success("Hi World!");
    }

    @RequestMapping("/app/hello")
    public ResponseResult say(){
        return ResponseUtil.success("Hello. This is app.");
    }

}
