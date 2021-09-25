package com.tencent.nft.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreType
import com.fasterxml.jackson.annotation.JsonView
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
    @JsonIgnore
    @JsonView(BaseViewGroup::class)
    var createTime: LocalDateTime? = LocalDateTime.now()

//    @LastModifiedDate
    @JsonIgnore
    @JsonView(BaseViewGroup::class)
    var updateTime: LocalDateTime? = LocalDateTime.now()
}