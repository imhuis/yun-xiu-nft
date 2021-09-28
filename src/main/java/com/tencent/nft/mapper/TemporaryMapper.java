package com.tencent.nft.mapper;

import com.tencent.nft.entity.security.Temporarysave;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
@Mapper
public interface TemporaryMapper {

    //接收前端发送的字符串，加入到数据库中
   int linshi(Temporarysave temporarysave);

    List<Temporarysave> selectlinshi(Temporarysave temporarysave);
}
