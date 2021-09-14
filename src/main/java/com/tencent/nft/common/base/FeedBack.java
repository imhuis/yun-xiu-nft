package com.tencent.nft.common.base;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class FeedBack implements Serializable {
    private Integer id; //ID主键自增
    private String name;
    private int phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private String picture;
    private String message;
}
