package com.tencent.nft.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.io.Serializable
import java.time.LocalDateTime

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
open class BaseEntity : Serializable {

    var id: Long? = null

//    @CreatedDate
    var createTime: LocalDateTime? = LocalDateTime.now()

//    @LastModifiedDate
    var updateTime: LocalDateTime? = LocalDateTime.now()
}