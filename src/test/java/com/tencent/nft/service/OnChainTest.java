package com.tencent.nft.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tencent.nft.entity.chain.CreateData;
import com.tencent.nft.entity.chain.OnChainResponse;
import com.tencent.nft.service.handler.OnChainHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: imhuis
 * @date: 2021/9/30
 * @description:
 */
@SpringBootTest
@Slf4j
public class OnChainTest {

    @Autowired
    private OnChainHandler onChainHandler;

//    @Test
    public void test() throws JsonProcessingException {
        String token = onChainHandler.getAccessToken();
        CreateData createData = new CreateData();
        createData.setHashType(0);
        createData.setEvidenceInfo("test111");

        String response = onChainHandler.createDataDeposit(createData, token);
        log.info(response);

    }
}
