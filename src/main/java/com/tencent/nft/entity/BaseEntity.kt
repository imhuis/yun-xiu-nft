package com.tencent.nft.entity

import com.fasterxml.jackson.annotation.JsonView
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

    interface BaseViewGroup

    var id: Long? = null

//    @CreatedDate
    @JsonView(BaseViewGroup::class)
    var createTime: LocalDateTime? = LocalDateTime.now()

//    @LastModifiedDate
    @JsonView(BaseViewGroup::class)
    var updateTime: LocalDateTime? = LocalDateTime.now()
}