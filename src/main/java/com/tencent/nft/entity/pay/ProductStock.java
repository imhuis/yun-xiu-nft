package com.tencent.nft.entity.pay;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2021/9/28
 * @description:
 */
public class ProductStock implements Serializable {

    private Long productId;
    private int stock;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
