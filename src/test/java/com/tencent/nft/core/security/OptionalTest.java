package com.tencent.nft.core.security;

import com.tencent.nft.entity.nft.NFTInfo;

import java.util.Optional;

/**
 * @author: imhuis
 * @date: 2021/9/27
 * @description:
 */
public class OptionalTest {

    public static void main(String[] args) {
        NFTInfo nftInfo = null;
        nftInfo = Optional.ofNullable(nftInfo).orElse(new NFTInfo());
    }
}
