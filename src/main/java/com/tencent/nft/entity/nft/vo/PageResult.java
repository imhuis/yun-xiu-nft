package com.tencent.nft.entity.nft.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PageResult {

    // 用户查询的数据
    private String query;

    // 页数
    private Integer pageNum;

    // 每页的条数
    private Integer pageSize;

    // 总记录数
    private Long total;

    // 分页后的结果
    private Object rows;

}
