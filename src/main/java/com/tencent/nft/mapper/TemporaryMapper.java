package com.tencent.nft.mapper;

import com.tencent.nft.common.base.FeedBack;
import com.tencent.nft.entity.security.Temporarysave;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Component
@Transactional
public interface TemporaryMapper {
    //接收前端发送的字符串，加入到数据库中
   int linshi(Temporarysave temporarysave);

}
