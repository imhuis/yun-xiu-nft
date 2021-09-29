package com.tencent.nft.entity.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
public class TemporaryRecord implements Serializable {

    private String key;
    @JsonProperty("save")
    private String record;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "TemporaryRecord{" +
                "key='" + key + '\'' +
                ", record='" + record + '\'' +
                '}';
    }
}
