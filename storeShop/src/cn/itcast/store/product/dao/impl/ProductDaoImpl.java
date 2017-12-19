package cn.itcast.store.product.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.store.product.dao.IProductDao;
import cn.itcast.store.product.domain.Category;
import cn.itcast.store.product.domain.Product;
import cn.itcast.store.utils.C3P0Utils;
import cn.itcast.store.utils.PageBean;

public class ProductDaoImpl implements IProductDao {

    /**
     * ����pid��ѯ������Ʒ������Ϣ
     */
    @Override
    public Product findProductInfoByPid(String pid) {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "select * from product where pid=?";
            // 3.����ʵ�ʲ���
            Object[] params = { pid };
            // 4.ִ�в�ѯ����
            return qr.query(sql, new BeanHandler<>(Product.class), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * �����cid��ѯĳ������������Ʒ���ܼ�¼�� total
     */
    @Override
    public int getTotalByCid(String cid) {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "select count(*) from product where cid=?";
            // 3.����ʵ�ʲ���
            Object[] params = { cid };
            // 4.ִ�в�ѯ����
            Long count = (Long) qr.query(sql, new ScalarHandler(), params);
            return count.intValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ����cid��ҳ��ѯĳ�������������е���Ʒ��Ϣ
     */
    @Override
    public List<Product> findAllProductByCidForPage(String cid, PageBean<Product> pageBean) {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "select * from product where cid=? and pflag=? limit ?,?";
            // 3.����ʵ�ʲ���
            Object[] params = { cid, 0, pageBean.getStartIndex(), pageBean.getPageSize() };
            // 4.ִ�в�ѯ����
            return qr.query(sql, new BeanListHandler<>(Product.class), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ��ѯ������Ʒ��Ϣ
     */
    @Override
    public List<Product> findAllHotProduct() {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "select * from product where pflag=? and is_hot=? order by pdate desc limit ?";
            // 3.����ʵ�ʲ���
            Object[] params = { 0, 1, 9 };
            // 4.ִ�в�ѯ����
            return qr.query(sql, new BeanListHandler<>(Product.class), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ��ѯ������Ʒ��Ϣ
     */
    @Override
    public List<Product> findAllNewProduct() {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "select * from product where pflag=? order by pdate desc limit ?";
            // 3.����ʵ�ʲ���
            Object[] params = { 0, 9 };
            // 4.ִ�в�ѯ����
            return qr.query(sql, new BeanListHandler<>(Product.class), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ��ѯ���з�����Ϣ
     */
    @Override
    public List<Category> findAllCategory() {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "select * from category";
            // 3.����ʵ�ʲ���
            Object[] params = {};
            // 4.ִ�в�ѯ����
            return qr.query(sql, new BeanListHandler<>(Category.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
