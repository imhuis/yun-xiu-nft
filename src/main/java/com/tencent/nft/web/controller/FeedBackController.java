package com.tencent.nft.web.controller;


import com.github.pagehelper.PageHelper;

import com.github.pagehelper.PageInfo;
import com.tencent.nft.common.base.FeedBack;
import com.tencent.nft.entity.nft.vo.SysResult;
import com.tencent.nft.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedBackController {
    @Autowired
    private FeedService feedService;

    /**
     * 接收前端客户的反馈信息
     * 插入数据库
     * */
    @PostMapping("/feedback")
    public SysResult insert(@RequestBody FeedBack feedBack){
        feedService.insert(feedBack);
        return SysResult.success();
    }

    /**
     *查询所有用户反馈信息
     * 并且进行分页
     * 每页数据15条
     * */
    @GetMapping("/getAllFeedBack")
    public PageInfo<FeedBack> getAllPerson(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,15);
        List<FeedBack> list = feedService.getAllFeedBack();
        PageInfo<FeedBack> pageInfo = new PageInfo<FeedBack>(list);
        model.addAttribute("pageInfo",pageInfo);
        return pageInfo;
    }


    /**
     * 根据日期进行查询
     * */
    @GetMapping("/getByDate/{date}")
    public SysResult getByDate(@PathVariable String date){
        List<FeedBack> byDate = feedService.getByDate(date);
        return SysResult.success(byDate);
    }

    /**
     * 根据用户的id删除反馈记录
     * */
    @DeleteMapping("/delete/{id}")
    public SysResult deleteById(@PathVariable Integer id){
        feedService.deleteById(id);
        return SysResult.success();
    }

    /**
     * 根据id查询
     * */
    @GetMapping("/getById/{id}")
    public SysResult getById(@PathVariable Integer id){
        feedService.getById(id);
        return SysResult.success(feedService.getById(id));
    }

}
