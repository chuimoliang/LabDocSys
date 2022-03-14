package com.moliang.labdocsys.data;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * @author zhang qing
 * @date 2022/3/14 10:18
 */
@Data
public class DataPage<T> {

    /**
     * 当前页码
     */
    private long current;

    /**
     * 当前页数量
     */
    private long pageSize;

    /**
     * 总页码
     */
    private long totalPage;

    /**
     * 总个数
     */
    private long total;

    /**
     * 数据列表
     */
    private List<T> data;

    public DataPage() {

    }

    public DataPage(List<T> data, IPage page) {
        this.current = page.getCurrent();
        this.pageSize = data.size();
        this.totalPage = page.getPages();
        this.total = page.getTotal();
        this.data = data;
    }

    public DataPage(List<T> data, int num) {
        this(data, num, num, 1, 1);
    }

    public DataPage(List<T> data, int pageSize, int total, int totalPage, int current) {
        this.current = current;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.total = total;
        this.data = data;
    }

}
