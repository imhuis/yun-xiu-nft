package com.tencent.nft.entity.pay

import com.tencent.nft.common.enums.pay.TradeState
import com.tencent.nft.entity.BaseEntity
import java.io.Serializable
import java.time.LocalDateTime

/**
 * @author: imhuis
 * @date: 2021/9/26
 * @description: 订单详情（t_trade）
 */
class TradeInfo : BaseEntity(), Serializable {
    var tradeNo: String? = null

    // 交易状态
    var tradeStatus: TradeState? = null
    var transactionId: String? = null
    var description: String? = null
    var payer: String? = null
    // 订单金额
    var amount = 0

    // 用户交易金额
    var payerTotal = 0
    var successTime: LocalDateTime? = null
}