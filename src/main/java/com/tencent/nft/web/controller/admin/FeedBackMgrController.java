package com.tencent.nft.web.controller.admin;


import com.github.pagehelper.PageHelper;

import com.github.pagehelper.PageInfo;
import com.tencent.nft.common.base.FeedBack;
import com.tencent.nft.entity.nft.vo.SysResult;
import com.tencent.nft.service.IFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
@RestController
public class FeedBackMgrController {

    @Autowired
    private IFeedService IFeedService;

    /**
     * 查询所有用户反馈信息
     * 并且进行分页
     * 每页数据15条
     */
    @PostMapping("/admin/getAllFeedBack")
    public SysResult getAllFeedBack(Model model, @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pagesize,
                                    @RequestParam(value = "date", required = false) String date) throws ParseException {
        PageHelper.startPage(pageNum, pagesize);
        List<FeedBack> list = null;
        try {
            list = IFeedService.getAll(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PageInfo<FeedBack> pageInfo = new PageInfo<FeedBack>(list);
        model.addAttribute("pageInfo", pageInfo);
        return SysResult.success(pageInfo);
    }


    /**
     * 根据日期进行查询
     */
    @GetMapping("/admin/getByDate/{date}")
    public SysResult getByDate(@PathVariable String date) {
        List<FeedBack> byDate = IFeedService.getByDate(date);
        return SysResult.success(byDate);
    }

    /**
     * 根据用户的id删除反馈记录
     */
    @DeleteMapping("/admin/delete/{id}")
    public SysResult deleteById(@PathVariable Integer id) {
        IFeedService.deleteById(id);
        return SysResult.success();
    }

    /**
     * 根据id查询
     */
    @GetMapping("/admin/getById/{id}")
    public SysResult getById(@PathVariable Integer id) {
        IFeedService.getById(id);
        return SysResult.success(IFeedService.getById(id));
    }

}
