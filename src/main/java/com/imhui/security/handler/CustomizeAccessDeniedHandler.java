package com.imhui.security.handler;

import com.imhui.security.common.base.ResponseResult;
import com.imhui.security.common.base.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setSuccess(false).setMessage("access denied");
        ResponseUtil.out(response, responseResult);
    }
}
