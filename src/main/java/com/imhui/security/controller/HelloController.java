package com.imhui.security.controller;

import com.imhui.security.common.base.ResponseResult;
import com.imhui.security.common.base.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @RequestMapping("/public/hello")
    public ResponseResult<String> hello(){
        return ResponseUtil.success("Hello World!");
    }
}
