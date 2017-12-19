package cn.itcast.store.product.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.store.product.dao.IAdminProductDao;
import cn.itcast.store.product.domain.Category;
import cn.itcast.store.utils.C3P0Utils;

public class AdminProductDaoImpl implements IAdminProductDao {

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

    /**
     * ��ӷ�����Ϣ
     */
    @Override
    public int addCategory(Category category) {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "insert into category values(?,?)";
            // 3.����ʵ�ʲ���
            Object[] params = { category.getCid(), category.getCname() };
            // 4.ִ����Ӳ���
            return qr.update(sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ����cidɾ��������Ϣ
     */
    @Override
    public int delCategoryByCid(String cid) {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "delete from category where cid=?";
            // 3.����ʵ�ʲ���
            Object[] params = { cid };
            // 4.ִ��ɾ������
            return qr.update(sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ����cid��ѯ������Ϣ
     */
    @Override
    public Category findCategoryByCid(String cid) {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "select * from category where cid=?";
            // 3.����ʵ�ʲ���
            Object[] params = { cid };
            // 4.ִ�в�ѯ����
            return qr.query(sql, new BeanHandler<>(Category.class), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ����cid�޸ķ�����Ϣ
     */
    @Override
    public int editCategoryByCid(Category category) {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "update category set cname=? where cid=?";
            // 3.����ʵ�ʲ���
            Object[] params = { category.getCname(), category.getCid() };
            // 4.ִ�и��²���
            return qr.update(sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
