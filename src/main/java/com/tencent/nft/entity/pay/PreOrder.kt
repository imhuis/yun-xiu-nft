package com.tencent.nft.entity.pay

import com.tencent.nft.entity.BaseEntity
import java.io.Serializable

/**
 * @author: imhuis
 * @date: 2021/9/29
 * @description:
 */
class PreOrder : BaseEntity(), Serializable {

    // 系统内部业务流水
    var tradeNo: String? = null

    // 商品编号
    var productNo: String? = null

    // 单价
    var price: Int? = 0

    // 支付人（openid）
    var payer: String? = null
}