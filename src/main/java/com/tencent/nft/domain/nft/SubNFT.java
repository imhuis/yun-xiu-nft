package com.tencent.nft.domain.nft;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description: 子NFT
 */
public class SubNFT extends SuperNFT implements Serializable {

    // 区块链地址
    private String address;

    // 子NFT创建时间
    private LocalDateTime nftCreateTime;
}
