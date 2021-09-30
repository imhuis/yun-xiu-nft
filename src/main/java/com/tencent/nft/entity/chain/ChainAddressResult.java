package com.tencent.nft.entity.chain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/9/30
 * @description:
 */
public class ChainAddressResult implements Serializable {

    @JsonProperty("RequestId")
    private String requestId;

    @JsonProperty("EvidenceId")
    private String evidenceId;

    @JsonProperty("EvidenceTime")
    private String evidenceTime;

    @JsonProperty("EvidenceTxHash")
    private String evidenceTxHash;

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

    public String getEvidenceTime() {
        return evidenceTime;
    }

    public void setEvidenceTime(String evidenceTime) {
        this.evidenceTime = evidenceTime;
    }

    public String getEvidenceTxHash() {
        return evidenceTxHash;
    }

    public void setEvidenceTxHash(String evidenceTxHash) {
        this.evidenceTxHash = evidenceTxHash;
    }

    @Override
    public String toString() {
        return "ChainAddressResult{" +
                "requestId='" + requestId + '\'' +
                ", evidenceId='" + evidenceId + '\'' +
                ", evidenceTime='" + evidenceTime + '\'' +
                ", evidenceTxHash='" + evidenceTxHash + '\'' +
                '}';
    }
}
