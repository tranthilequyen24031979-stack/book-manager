package com.example.book_manger.util;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageUtils {

    public static List<Integer> getPageNumbers(Page<?> page) {
        List<Integer> pageNumbers = new ArrayList<>();
        int totalPages = page.getTotalPages();
        int currentPage = page.getNumber();

        int startPage = Math.max(0, currentPage - 2);
        int endPage = Math.min(totalPages - 1, currentPage + 2);

        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }

        return pageNumbers;
    }

    public static boolean isFirstPage(Page<?> page) {
        return page.getNumber() == 0;
    }

    public static boolean isLastPage(Page<?> page) {
        return page.getNumber() == page.getTotalPages() - 1;
    }

    public static int getStartRecordNumber(Page<?> page) {
        return page.getNumber() * page.getSize() + 1;
    }

    public static int getEndRecordNumber(Page<?> page) {
        return Math.min((page.getNumber() + 1) * page.getSize(), (int) page.getTotalElements());
    }
}