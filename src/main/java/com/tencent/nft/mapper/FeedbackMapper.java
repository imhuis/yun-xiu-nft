package com.tencent.nft.mapper;

import com.tencent.nft.common.base.FeedBack;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
@Mapper
public interface FeedbackMapper {

    //接收前端数据并加入数据库
    int insert(FeedBack feedBack);

    //根据日期查询
    List<FeedBack> getByDate(LocalDateTime dateTime, LocalDateTime localDateTime);

    //根据id删除
    int deleteById(Integer id);

    //查询全部数据并进行分页
    List<FeedBack> getAll(FeedBack feedBack);

    //根据ID查询
    Object getById(Integer id);
}
