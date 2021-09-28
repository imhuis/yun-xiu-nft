package com.tencent.nft.entity.nft.vo

/**
 * @author: imhuis
 * @date: 2021/9/18
 * @description:
 */
class BoxLabelVO {

    var value = 0
    var label: String? = null

    constructor(value: Int, label: String?) {
        this.value = value
        this.label = label
    }
}