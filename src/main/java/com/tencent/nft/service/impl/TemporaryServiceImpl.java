package com.tencent.nft.service.impl;

import com.tencent.nft.entity.security.Temporarysave;
import com.tencent.nft.mapper.TemporaryMapper;
import com.tencent.nft.service.TemporaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TemporaryServiceImpl implements TemporaryService {

    @Resource
    private TemporaryMapper temporaryMapper;

    @Override
    public int linshi(Temporarysave temporarysave) {
        return temporaryMapper.linshi(temporarysave);
    }
}
