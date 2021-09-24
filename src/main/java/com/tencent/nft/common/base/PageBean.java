package com.tencent.nft.common.base;

import com.github.pagehelper.PageInfo;

import java.util.Collection;

/**
 * @author: imhuis
 * @date: 2021/9/9
 * @description:
 */
public class PageBean<T extends Collection> {

    private int pages;
    private long total;
    private T data;


    public PageBean() {
    }

    public PageBean(PageInfo pageInfo) {
        this.pages = pageInfo.getPages();
        this.total = pageInfo.getTotal();
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
