package com.tencent.nft.web.controller.admin;

import com.tencent.nft.entity.admin.pojo.Adminpojo;
import com.tencent.nft.entity.nft.vo.SysResult;
import org.junit.Test;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
@RestController
@CrossOrigin
public class AdminController {



    @GetMapping("/admindata")
    public Object Adminpojo() {
        Adminpojo c = new Adminpojo(60416, 3481, 1024, 59);

        return c;
    }
}
