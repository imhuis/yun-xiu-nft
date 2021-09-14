package com.tencent.nft.mapper;


import com.tencent.nft.common.base.FeedBack;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Component
@Transactional
public interface FeedMapper {
    //接收前端数据并加入数据库
    int insert(FeedBack feedBack);
    //根据日期查询
    List<FeedBack> getByDate(@Param("A") LocalDate localDate);
    //根据id删除
    int deleteById(Integer id);
    //查询全部数据并进行分页
    List<FeedBack> getAll(FeedBack feedBack);
    //根据ID查询
    Object getById(Integer id);
}
