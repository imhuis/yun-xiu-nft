package com.imhui.security.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "createTime", column = @Column(name = "create_time")),
            @AttributeOverride(name = "updateTime", column = @Column(name = "update_time"))
    })
    private AuditMetaData auditMetaData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuditMetaData getAuditMetaData() {
        return auditMetaData;
    }

    public void setAuditMetaData(AuditMetaData auditMetaData) {
        this.auditMetaData = auditMetaData;
    }
}

class AuditMetaData {

    @CreatedDate
    @Column(name = "create_time")
    private LocalDateTime createTime;

//    @CreatedBy
//    private Long createBy;

    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
