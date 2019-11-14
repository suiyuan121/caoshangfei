package com.zj.caoshangfei.utils;


import org.springframework.stereotype.Service;

/**
 * 工具 Created by jin.zhang@fuwo.com on 2017/12/29.
 */
@Service
public class PaginationUtils {

    private static final int MAX_PAGE_NUM = 50;

    public static String pagination(int totalPages, int currentPage, String url) {

        int maxPageNum = (totalPages > MAX_PAGE_NUM ? MAX_PAGE_NUM : totalPages);

        if (totalPages <= 1) {
            //分页少于等于一页的话，什么都不显示
            return "";
        }
        if (currentPage >= totalPages) {
            //当前页大于等于总页数，则跳转至最后一个
            currentPage = totalPages;
        }

        //上一页

        String last = "<a class=\"dn\" href='" + (currentPage == 1 ? "javascript:void(0);" : ((url + (currentPage - 1)))) + "'>上一页</a>";

        String next = "<a class=\"dn\" href='" + (currentPage == maxPageNum ? "javascript:void(0);" : (url + (Integer.valueOf(currentPage) + 1))) + "'>下一页</a>";


        String html = last;
        //往前两页
        if (currentPage - 2 > 0) {
            html = html + "<a href='" + (url + (currentPage - 2)) + "'>" + (currentPage - 2) + "</a>";
        }
        //往前一页
        if (currentPage - 1 > 0) {
            html = html + "<a href='" + (url + (currentPage - 1)) + "'>" + (currentPage - 1) + "</a>";
        }

        //当前页
        html = html + "<a class=\"h\" href='" + (url + (currentPage)) + "'>" + (currentPage) + "</a>";

        //后一页
        if (currentPage + 1 <= totalPages) {
            html = html + "<a href='" + (url + (currentPage + 1)) + "'>" + (currentPage + 1) + "</a>";
        }

        //后两页
        if (currentPage + 2 <= totalPages) {
            html = html + "<a href='" + (url + (currentPage + 2)) + "'>" + (currentPage + 2) + "</a>";
        }
        html += next;
        return html;

    }

}
