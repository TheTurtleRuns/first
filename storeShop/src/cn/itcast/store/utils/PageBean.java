package cn.itcast.store.utils;

import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable {
    
    // 2.总记录数(查询数据库)
    private int total;

    // 1.分页查询的数据(查询数据库)
    private List<T> rows;

    // 3.每页显示大小(固定写死)
    private int pageSize;

    // 4.要查看的页面(页面用户传递)
    private int pageNumber;

    // 5.总页码数(计算得来)
    private int totalPage;

    // 6.起始索引(计算得来)
    private int startIndex;
    
    //单独定义一个方法，供分页的样式使用
    public int getSize(){
        if(rows!=null){
            return rows.size();
        }
        return 0;
    }

    public int getTotalPage() {
        return totalPage = this.getTotal() % this.getPageSize() == 0 ? (this.getTotal() / this.getPageSize())
                : (this.getTotal() / this.getPageSize() + 1);
    }

    /*
     * public void setTotalPage(int totalPage) { this.totalPage = totalPage; }
     */

    public int getStartIndex() {
        return startIndex = (this.getPageNumber() - 1) * pageSize;
    }

    /*
     * public void setStartIndex(int startIndex) { this.startIndex = startIndex; }
     */

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public PageBean() {

    }

}
