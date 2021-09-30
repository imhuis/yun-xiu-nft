package com.tencent.nft.common.base;


import com.tencent.nft.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;


/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
@Data
@Accessors(chain = true)
public class FeedBack extends BaseEntity implements Serializable {

    private String name;
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private String picture;
    @NotBlank(message = "")
    private String message;
}
