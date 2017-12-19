package cn.itcast.store.utils;

import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable {
    
    // 2.�ܼ�¼��(��ѯ���ݿ�)
    private int total;

    // 1.��ҳ��ѯ������(��ѯ���ݿ�)
    private List<T> rows;

    // 3.ÿҳ��ʾ��С(�̶�д��)
    private int pageSize;

    // 4.Ҫ�鿴��ҳ��(ҳ���û�����)
    private int pageNumber;

    // 5.��ҳ����(�������)
    private int totalPage;

    // 6.��ʼ����(�������)
    private int startIndex;
    
    //��������һ������������ҳ����ʽʹ��
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
