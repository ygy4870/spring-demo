/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.ygy.study.mybatisdemo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 分页类
 *
 * @param <T>
 * @author ThinkGem
 * @version 2013-7-2
 */
public class Page<T> implements Serializable {

    /**
     * SQL过滤，防止注入
     */
    private static final String REG = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|drop|execute)\\b)";
    private static final Pattern SQL_PATTERN = Pattern.compile(REG, Pattern.CASE_INSENSITIVE);

    private int pageNo = 1; // 当前页码
    private int pageSize = 20; // 页面大小，设置为“-1”表示不进行分页（分页无效）

    private long total;// 总记录数，设置为“-1”表示不查询总数

    private int first;// 首页索引
    private int last;// 尾页索引
    private int prev;// 上一页索引
    private int next;// 下一页索引

    private boolean firstPage;//是否是第一页
    private boolean lastPage;//是否是最后一页

    private List<T> list = new ArrayList<T>();

    private String orderBy = ""; // 标准查询有效， 实例： updatedate desc, name asc

    private String funcName = "page"; // 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用。

    private String funcParam = ""; // 函数的附加参数，第三个参数值。

    private String message = ""; // 设置提示消息，显示在“共n条”之后

    public Page() {
        this.pageSize = -1;
    }

    /**
     * 构造方法
     *
     * @param pageNo   当前页码
     * @param pageSize 分页大小
     */
    public Page(int pageNo, int pageSize) {
        this(pageNo, pageSize, 0);
    }

    /**
     * 构造方法
     *
     * @param pageNo   当前页码
     * @param pageSize 分页大小
     * @param total    数据条数
     */
    public Page(int pageNo, int pageSize, long total) {
        this(pageNo, pageSize, total, new ArrayList<T>());
    }

    /**
     * 构造方法
     *
     * @param pageNo   当前页码
     * @param pageSize 分页大小
     * @param total    数据条数
     * @param list     本页数据对象列表
     */
    public Page(int pageNo, int pageSize, long total, List<T> list) {
        this.setTotal(total);
        this.setPageNo(pageNo);
        this.pageSize = pageSize == 0 ? 20 : pageSize;
        this.list = list;
    }

    /**
     * 初始化参数
     */
    public void initialize() {

        //1
        this.first = 1;

        this.last = (int) (total / (this.pageSize < 1 ? 20 : this.pageSize) + first - 1);

        if (this.total % this.pageSize != 0 || this.last == 0) {
            this.last++;
        }

        if (this.last < this.first) {
            this.last = this.first;
        }

        if (this.pageNo <= 1) {
            this.pageNo = this.first;
            this.firstPage = true;
        }

        if (this.pageNo >= this.last) {
            this.pageNo = this.last;
            this.lastPage = true;
        }

        if (this.pageNo < this.last - 1) {
            this.next = this.pageNo + 1;
        } else {
            this.next = this.last;
        }

        if (this.pageNo > 1) {
            this.prev = this.pageNo - 1;
        } else {
            this.prev = this.first;
        }

        //2
        if (this.pageNo < this.first) {// 如果当前页小于首页
            this.pageNo = this.first;
        }

        if (this.pageNo > this.last) {// 如果当前页大于尾页
            this.pageNo = this.last;
        }

    }

    /**
     * 默认输出当前分页标签
     * <div class="page">${page}</div>
     */
    @Override
    public String toString() {
        return "";
    }

    /**
     * 获取分页HTML代码
     *
     * @return
     */
    public String getHtml() {
        return toString();
    }

    /**
     * 获取设置总数
     *
     * @return
     */
    public long getTotal() {
        return total;
    }

    /**
     * 设置数据总数
     *
     * @param total
     */
    public void setTotal(long total) {
        this.total = total;
        if (pageSize >= total) {
            pageNo = 1;
        }
    }


    /**
     * 获取当前页码
     *
     * @return
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前页码
     *
     * @param pageNo
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 获取页面大小
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置页面大小（最大500）
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? 10 : pageSize;// > 500 ? 500 : pageSize;
    }

    /**
     * 首页索引
     *
     * @return
     */

    public int getFirst() {
        return first;
    }

    /**
     * 尾页索引
     *
     * @return
     */

    public int getLast() {
        return last;
    }

    /**
     * 获取页面总数
     *
     * @return getLast();
     */
    public int getTotalPage() {
        return getLast();
    }

    /**
     * 是否为第一页
     *
     * @return
     */

    public boolean isFirstPage() {
        return firstPage;
    }

    /**
     * 是否为最后一页
     *
     * @return
     */

    public boolean isLastPage() {
        return lastPage;
    }

    /**
     * 上一页索引值
     *
     * @return
     */

    public int getPrev() {
        if (isFirstPage()) {
            return pageNo;
        } else {
            return pageNo - 1;
        }
    }

    /**
     * 下一页索引值
     *
     * @return
     */

    public int getNext() {
        if (isLastPage()) {
            return pageNo;
        } else {
            return pageNo + 1;
        }
    }

    /**
     * 获取本页数据对象列表
     *
     * @return List<T>
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 设置本页数据对象列表
     *
     * @param list
     */
    public Page<T> setList(List<T> list) {
        this.list = list;
        initialize();
        return this;
    }

    /**
     * 获取查询排序字符串
     *
     * @return
     */

    public String getOrderBy() {
        if (orderBy == null || orderBy.isEmpty()) {
            return "";
        }
        if (SQL_PATTERN.matcher(orderBy).find()) {
            return "";
        }
        return orderBy;
    }

    /**
     * 设置查询排序，标准查询有效， 实例： updatedate desc, name asc
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * 获取点击页码调用的js函数名称
     * function ${page.funcName}(pageNo){location="${ctx}/list-${category.id}${urlSuffix}?pageNo="+i;}
     *
     * @return
     */

    public String getFuncName() {
        return funcName;
    }

    /**
     * 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用。
     *
     * @param funcName 默认为page
     */
    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    /**
     * 获取分页函数的附加参数
     *
     * @return
     */

    public String getFuncParam() {
        return funcParam;
    }

    /**
     * 设置分页函数的附加参数
     *
     * @return
     */
    public void setFuncParam(String funcParam) {
        this.funcParam = funcParam;
    }

    /**
     * 设置提示消息，显示在“共n条”之后
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 分页是否有效
     *
     * @return this.pageSize==-1
     */

    public boolean isDisabled() {
        return this.pageSize == -1;
    }

    /**
     * 是否进行总数统计
     *
     * @return this.total==-1
     */

    public boolean isNotTotal() {
        return this.total == -1;
    }

    /**
     * 获取 Hibernate FirstResult
     */
    public int getFirstResult() {
        int firstResult = (getPageNo() - 1) * getPageSize();
//		if (firstResult >= getTotal()) {
//			firstResult = 0;
//		}
        return firstResult;
    }

    /**
     * 获取 Hibernate MaxResults
     */
    public int getMaxResults() {
        return getPageSize();
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public static <T> Page<T> emptyPage() {
        return new Page<>();
    }
}
