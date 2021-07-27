package com.imhui.security.web.controller;

import com.imhui.security.common.base.ResponseResult;
import com.imhui.security.common.base.ResponseUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("")
    public ResponseResult me(){
        SecurityContextHolder.getContext().getAuthentication();
        return ResponseUtil.success();
    }
}
