package com.tencent.nft.entity.chain;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/9/28
 * @description:
 */
public class OnChainObject implements Serializable {

    // 业务 ID 长度最大不超过 64
    private String evidenceId;
    // 业务数据特征值或业务数据明文(json 格式字符串)
    private String evidenceType;

    // 算法类型 0 SHA256, 1 SHA384, 2 SM3 默认 0
    private String hashType;

    private String evidenceInfo;

    public String getEvidenceId() {
        return evidenceId;
    }

    public void setEvidenceId(String evidenceId) {
        this.evidenceId = evidenceId;
    }

    public String getEvidenceType() {
        return evidenceType;
    }

    public void setEvidenceType(String evidenceType) {
        this.evidenceType = evidenceType;
    }

    public String getHashType() {
        return hashType;
    }

    public void setHashType(String hashType) {
        this.hashType = hashType;
    }

    public String getEvidenceInfo() {
        return evidenceInfo;
    }

    public void setEvidenceInfo(String evidenceInfo) {
        this.evidenceInfo = evidenceInfo;
    }
}
