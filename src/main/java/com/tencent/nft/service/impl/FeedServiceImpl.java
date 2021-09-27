package com.tencent.nft.service.impl;



import com.github.pagehelper.util.StringUtil;
import com.tencent.nft.common.base.FeedBack;
import com.tencent.nft.mapper.FeedbackMapper;
import com.tencent.nft.service.FeedService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
@Service
public class FeedServiceImpl implements FeedService {

    @Resource
    private FeedbackMapper feedbackMapper;


    @Override
    public int insert(FeedBack feedBack) {
        if(feedBack.getMessage() == null || feedBack.getMessage() == "") {
                return 1;
        }
        return feedbackMapper.insert(feedBack);
    }

    /**
     * 转换接收的日期格式
     * */
    @Override
    public List<FeedBack> getByDate(String date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDateTime = LocalDate.parse(date, df);
        return feedbackMapper.getByDate(localDateTime);
    }


    @Override
    public boolean deleteById(Integer id) {
        int i = feedbackMapper.deleteById(id);
        if(i > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<FeedBack> getAll(String date) throws ParseException {
        FeedBack feedBack=new FeedBack();
        if(!StringUtil.isEmpty(date)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            feedBack.setDate(sdf.parse(date));
        }

        return feedbackMapper.getAll(feedBack);
    }

    @Override
    public Object getById(Integer id) {
        return feedbackMapper.getById(id);
    }
}
