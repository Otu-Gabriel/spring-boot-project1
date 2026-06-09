package com.userApi.userApi.response;

import java.util.List;

public class PaginationResponse<T> {

    private List<T> items;
    private int currentPage;
    private int pageSize;
    private long totalItems;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;

    public PaginationResponse(
            List<T> items,
            int currentPage,
            int pageSize,
            long totalItems,
            int totalPages,
            boolean hasNext,
            boolean hasPrevious
    ){
        this.items = items;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }


    public List<T> getItems() {
        return items;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }
    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }
}
