package com.wj.babapao.http;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HuXiangLiang
 * @name TomatoLive
 * @class name：com.tomatolive.library.http
 * @class describe
 * @time 2018/9/4 0004 14:47
 * @change
 * @chang time
 * @class describe
 */

public class HttpResultPageModel<T> {

    public int pageNumber = 1;
    public int pageSize = 0;
    public int totalRowsCount = 0;
    public int totalPageCount = 1;
    public String totalGold = "0";       //收支明细中的总金额

    public List<T> dataList = new ArrayList<>();

    public boolean isHasBanner = false;

    public boolean isMorePage() {
        return totalPageCount == 0 || pageNumber == 0 || pageNumber >= totalPageCount;
    }

    public boolean isNoEmptyData() {
        return totalRowsCount > 0;
    }

    @Override
    public String toString() {
        return "HttpResultPageModel{" +
                "dataList=" + dataList +
                '}';
    }
}
