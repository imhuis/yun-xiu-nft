package com.tencent.nft.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
@MappedSuperclass
open class BaseEntity : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @CreatedDate
    @Column(name = "create_time")
    var createTime: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "update_time")
    var updateTime: LocalDateTime? = null
}