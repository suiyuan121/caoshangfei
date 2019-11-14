package com.zj.caoshangfei.common.bean;

import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jin.zhang@fuwo.com on 2017/12/5.
 */
public class PageList<T> implements Serializable {

    private int page;

    private int size;

    private long total;

    private List<T> content;

    private Pageable pageable;

    private int totalPages;

    public PageList() {
    }

    public PageList(Pageable pageable, long total, List<T> content) {
        this.pageable = pageable;
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();
        this.total = total;
        this.content = content;

        this.totalPages = getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());


    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
