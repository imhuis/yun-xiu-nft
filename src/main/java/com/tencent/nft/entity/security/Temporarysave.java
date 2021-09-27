package com.tencent.nft.entity.security;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
@Data
@Accessors(chain = true)
public class Temporarysave implements Serializable {

    private String save;

}
