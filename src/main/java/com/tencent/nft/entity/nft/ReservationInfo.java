package com.tencent.nft.entity.nft;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/10/13
 * @description:
 */
public class ReservationInfo implements Serializable {

    // NFT编号
    private String nftId;

    @JsonProperty("yyrs")
    private int reservation;
}
