package com.tencent.nft.web.controller;


import com.github.pagehelper.PageHelper;

import com.github.pagehelper.PageInfo;
import com.tencent.nft.common.base.FeedBack;
import com.tencent.nft.entity.nft.vo.SysResult;
import com.tencent.nft.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
@RestController
@CrossOrigin
public class FeedBackController {
    @Autowired
    private FeedService feedService;

    /**
     * 接收前端客户的反馈信息
     * 插入数据库
     * */
    @PostMapping("/app/feedback")
    public SysResult insert(@RequestBody(required=false) FeedBack feedBack){
        feedService.insert(feedBack);
        return SysResult.success("null");
    }

    /**
     *查询所有用户反馈信息
     * 并且进行分页
     * 每页数据15条
     * */
    @PostMapping("/admin/getAllFeedBack")
    public SysResult getAllFeedBack(Model model, @RequestParam(value = "pageNum",required = false, defaultValue = "1") Integer pageNum,
     @RequestParam(value = "pageSize",required = false, defaultValue = "20") Integer pagesize,
     @RequestParam(value = "date", required = false) String date) throws ParseException {
        PageHelper.startPage(pageNum,pagesize);
        List<FeedBack> list = null;
        try {
            list = feedService.getAll(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PageInfo<FeedBack> pageInfo = new PageInfo<FeedBack>(list);
        model.addAttribute("pageInfo",pageInfo);
        return SysResult.success(pageInfo);
    }


    /**
     * 根据日期进行查询
     * */
    @GetMapping("/admin/getByDate/{date}")
    public SysResult getByDate(@PathVariable String date){
        List<FeedBack> byDate = feedService.getByDate(date);
        return SysResult.success(byDate);
    }

    /**
     * 根据用户的id删除反馈记录
     * */
    @DeleteMapping("/admin/delete/{id}")
    public SysResult deleteById(@PathVariable Integer id){
        feedService.deleteById(id);
        return SysResult.success();
    }

    /**
     * 根据id查询
     * */
    @GetMapping("/admin/getById/{id}")
    public SysResult getById(@PathVariable Integer id){
        feedService.getById(id);
        return SysResult.success(feedService.getById(id));
    }

}
