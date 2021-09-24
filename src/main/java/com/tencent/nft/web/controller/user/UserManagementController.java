package com.tencent.nft.web.controller.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.nft.common.base.FeedBack;
import com.tencent.nft.entity.nft.vo.SysResult;
import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.service.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/17
 * @description:
 */
@RestController
public class UserManagementController {
        @Resource
        private UserManagerService userManagerService;
    // 用户列表











    /**
     * 根据id查询
     * */
    @GetMapping("/selectById/{id}")
    public SysResult selectById(@PathVariable Integer id){
        userManagerService.selectById(id);
        return SysResult.success(userManagerService.selectById(id));
    }

    /**
     * 查询全部用户和条件查询
     * */
    @PostMapping("/selectAllUser")
    public SysResult selectAllUser(Model model,
        @RequestParam(value = "pageNum",required = false, defaultValue = "1") Integer pageNum,
        @RequestParam(value = "pageSize",required = false, defaultValue = "20") Integer pageSize,
        @RequestParam(value = "date", required = false)  String date,
        @RequestParam(value = "phone", required = false)  String phone)throws ParseException {
        PageHelper.startPage(pageNum,pageSize);
        List<WxUser> list = null;
        try {
            list = userManagerService.selectAllUser(date,phone);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PageInfo<WxUser> pageInfo = new PageInfo<WxUser>(list);
        model.addAttribute("pageInfo",pageInfo);
        return SysResult.success(pageInfo);
    }

}
