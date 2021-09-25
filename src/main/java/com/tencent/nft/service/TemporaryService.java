package com.tencent.nft.service;

import com.tencent.nft.entity.security.Temporarysave;

import java.util.List;

public interface TemporaryService {

    int linshi(Temporarysave temporarysave);

    List<Temporarysave> selectlinshi(Temporarysave temporarysave);
}
