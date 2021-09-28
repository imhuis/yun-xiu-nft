package com.tencent.nft.mapper;

import com.tencent.nft.entity.admin.TemporaryRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
@Mapper
public interface TemporaryMapper {

    //接收前端发送的字符串，加入到数据库中
    int insert(TemporaryRecord temporaryRecord);

    int update(TemporaryRecord temporaryRecord);

    String selectByKeyWord(String temporaryRecord);
}
