package com.tencent.nft.service;

import com.tencent.nft.entity.security.Temporarysave;

import java.util.List;

/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
public interface TemporaryService {

    int linshi(Temporarysave temporarysave);

    List<Temporarysave> selectlinshi(Temporarysave temporarysave);
}
