package com.tencent.nft.service.impl;

import com.tencent.nft.entity.admin.TemporaryRecord;
import com.tencent.nft.mapper.TemporaryMapper;
import com.tencent.nft.service.ITemporaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
@Service
public class TemporaryServiceImpl implements ITemporaryService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private TemporaryMapper temporaryMapper;

    @Override
    public int setupTemporaryRecord(TemporaryRecord temporaryRecord) {
        return temporaryMapper.insert(temporaryRecord);
    }

    @Transactional
    @Override
    public void updateTemporaryRecord(TemporaryRecord temporaryRecord) {
        String keyWord = "index-nftId";
        temporaryRecord.setKey(keyWord);
        temporaryMapper.update(temporaryRecord);

        StringBuilder sb = new StringBuilder("temp:");
        String redisKey = sb.append(keyWord).toString();
        BoundValueOperations<String,String> bvo = stringRedisTemplate.boundValueOps(redisKey);
        bvo.set(temporaryRecord.getRecord());
        bvo.expire(Duration.ofHours(1));
    }

    @Override
    public String selectByKeyWord(String key) {
        String keyWord = "index-nftId";
        StringBuilder sb = new StringBuilder("temp:");
        String redisKey = sb.append(keyWord).toString();
        Boolean flag = stringRedisTemplate.hasKey(redisKey);
        BoundValueOperations<String,String> bvo = stringRedisTemplate.boundValueOps(redisKey);
        if (flag){
            return bvo.get();
        }else {
            String s = temporaryMapper.selectByKeyWord(keyWord);
            bvo.setIfAbsent(s, Duration.ofHours(1));
            return s;
        }
    }

}
