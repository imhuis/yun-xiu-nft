package com.tencent.nft.web.controller.user;

import com.tencent.nft.entity.nft.vo.SysResult;
import com.tencent.nft.service.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

}
