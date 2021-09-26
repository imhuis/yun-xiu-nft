package com.tencent.nft.entity.admin.pojo;


import lombok.Data;
import lombok.experimental.Accessors;
/**
 * @author: yun
 * @date: 2021/9/26
 * @description:
 */


@Data
@Accessors(chain = true)
public class Adminpojo {
    //总交易金额
    private int totalamount;
    //今日交易金额
    private int todayamount;
    //总订单数量
    private int allorder;
    //今日订单数量
    private int todayorder;

    public Adminpojo(int totalamount, int todayamount, int allorder, int todayorder) {
        this.totalamount = totalamount;
        this.todayamount = todayamount;
        this.allorder = allorder;
        this.todayorder = todayorder;
    }
}
