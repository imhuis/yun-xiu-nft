package com.tencent.nft.entity.nft.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PageResult {
    private String  query;   //用户查询的数据
    private Integer pageNum; //页数
    private Integer pageSize;//每页的条数
    private Long    total;   //总记录数
    private Object  rows;    //分页后的结果
}
