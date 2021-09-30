package com.tencent.nft.entity.chain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/9/30
 * @description:
 */
public class OnChainResponse implements Serializable {

    @JsonProperty("RequestId")
    private String requestId;
    @JsonProperty("EvidenceId")
    private String evidenceId;
    @JsonProperty("BusinessId")
    private String businessId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getEvidenceId() {
        return evidenceId;
    }

    public void setEvidenceId(String evidenceId) {
        this.evidenceId = evidenceId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @Override
    public String toString() {
        return "OnChainResponse{" +
                "requestId='" + requestId + '\'' +
                ", evidenceId='" + evidenceId + '\'' +
                ", businessId='" + businessId + '\'' +
                '}';
    }
}
