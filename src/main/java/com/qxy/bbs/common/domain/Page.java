package com.qxy.bbs.common.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qixiangyang5
 * @create 2020/7/22 18:21
 */
public class Page<T> {

    private int number = 1;
    private int size = 10;
    private long totalElements;
    private List<T> content = new ArrayList();

    public Page() {
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Page number must be greater than zero!");
        } else {
            this.number = number;
        }
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero!");
        } else {
            this.size = size;
        }
    }

    public long getTotalElements() {
        return this.totalElements;
    }

    public void setTotalElements(long totalElements) {
        if (totalElements < 0L) {
            throw new IllegalArgumentException("Total elements count must be greater than zero!");
        } else {
            this.totalElements = totalElements;
        }
    }

    public long getTotalPages() {
        return this.getSize() == 0 ? 0L : (long)Math.ceil((double)this.totalElements / (double)this.getSize());
    }

    public boolean hasNextPage() {
        return (long)((this.getNumber() + 1) * this.getSize()) < this.totalElements;
    }

    public boolean hasPreviousPage() {
        return this.number - 1 >= 1;
    }

    public boolean isFirstPage() {
        return !this.hasPreviousPage();
    }

    public boolean isLastPage() {
        return !this.hasNextPage();
    }

    public boolean hasContent() {
        return !this.content.isEmpty();
    }

    public int getNumberOfElements() {
        return this.content.size();
    }

    public List<T> getContent() {
        return this.content;
    }

    public void setContent(List<T> content) {
        if (content != null && !content.isEmpty()) {
            this.content = content;
        }

    }


}
