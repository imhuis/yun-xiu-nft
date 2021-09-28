package com.tencent.nft.entity.chain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetAccessTokenResult {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("access_token")
    private String tokenType;

    @JsonProperty("expires_in")
    private String expires;

    @JsonProperty("scope")
    private String scope;
}
