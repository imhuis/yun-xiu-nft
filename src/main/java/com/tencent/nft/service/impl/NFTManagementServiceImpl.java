package com.tencent.nft.service.impl;

import com.tencent.nft.domain.nft.NFTInfo;
import com.tencent.nft.domain.nft.SuperNFT;
import com.tencent.nft.mapper.NFTMapper;
import com.tencent.nft.service.NFTManagementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NFTManagementServiceImpl implements NFTManagementService {

    @Resource
    private NFTMapper nftMapper;

    @Override
    public int createNFT(SuperNFT superNFT) {
        return 0;
    }

    @Override
    public List<NFTInfo> listNFT() {
        return null;
    }
}
