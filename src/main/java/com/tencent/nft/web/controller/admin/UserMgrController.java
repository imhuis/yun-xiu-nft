package com.tencent.nft.web.controller.admin;

import com.github.pagehelper.PageInfo;
import com.tencent.nft.entity.nft.vo.SysResult;
import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.service.UserManagerService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description:
 */
@RestController
@RequestMapping("/admin")
public class UserMgrController {

    @Resource
    private UserManagerService userManagerService;

    /**
     * 根据id查询
     * */
    @GetMapping("/wx_user/{id}")
    public SysResult selectById(@PathVariable Integer id){
        return SysResult.success(userManagerService.selectById(id));
    }

    /**
     * 查询全部用户和条件查询
     * */
    @GetMapping("/wx_user")
    public SysResult selectAllUser(Model model,
        @RequestParam(value = "page",required = false, defaultValue = "1") Integer pageNum,
        @RequestParam(value = "per_page",required = false, defaultValue = "20") Integer pageSize,
        @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date date,
        @RequestParam(value = "phone", required = false) String phone) {

        List<WxUser> list = null;
        try {
            list = userManagerService.selectAllUser(pageNum, pageSize, date, phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PageInfo<WxUser> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return SysResult.success(pageInfo);
    }

}
