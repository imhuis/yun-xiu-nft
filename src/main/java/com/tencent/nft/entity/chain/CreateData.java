package com.tencent.nft.entity.chain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/9/28
 * @description:
 */
public class CreateData implements Serializable {

    // 业务数据明文
    @JsonProperty("EvidenceInfo")
    private String evidenceInfo;
    // 存证名称
    @JsonProperty("EvidenceName")
    private String evidenceName;
    // 透传字段
    @JsonProperty("BusinessId")
    private String businessId;
    // 算法类型 0 SM3, 1 SHA256, 2 SHA384 默认0
    @JsonProperty("HashType")
    private Integer hashType;
    // 存证描述
    @JsonProperty("EvidenceDescription")
    private String evidenceDescription;

    public String getEvidenceInfo() {
        return evidenceInfo;
    }

    public void setEvidenceInfo(String evidenceInfo) {
        this.evidenceInfo = evidenceInfo;
    }

    public String getEvidenceName() {
        return evidenceName;
    }

    public void setEvidenceName(String evidenceName) {
        this.evidenceName = evidenceName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getHashType() {
        return hashType;
    }

    public void setHashType(Integer hashType) {
        this.hashType = hashType;
    }

    public String getEvidenceDescription() {
        return evidenceDescription;
    }

    public void setEvidenceDescription(String evidenceDescription) {
        this.evidenceDescription = evidenceDescription;
    }

    @Override
    public String toString() {
        return "CreateData{" +
                "evidenceInfo='" + evidenceInfo + '\'' +
                ", evidenceName='" + evidenceName + '\'' +
                ", businessId='" + businessId + '\'' +
                ", hashType=" + hashType +
                ", evidenceDescription='" + evidenceDescription + '\'' +
                '}';
    }
}
