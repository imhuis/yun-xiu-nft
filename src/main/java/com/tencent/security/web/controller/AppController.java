package com.tencent.security.web.controller;

import com.tencent.security.common.base.ResponseResult;
import com.tencent.security.common.base.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @RequestMapping("/app/hello")
    public ResponseResult say(){
        return ResponseUtil.success("Hello. This is app.");
    }
}
