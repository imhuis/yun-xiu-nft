package com.tencent.nft.service;

import com.tencent.nft.entity.admin.TemporaryRecord;

/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
public interface ITemporaryService {

    int setupTemporaryRecord(TemporaryRecord temporaryRecord);

    int updateTemporaryRecord(TemporaryRecord temporaryRecord);

    String selectByKeyWord(String temporaryRecord);
}
