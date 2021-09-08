package com.tencent.nft.common.base;

import java.util.Collection;
import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/9
 * @description:
 */
public class PageBean<T extends Collection> {

    private int pages;
    private int size;
    private T data;


    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
