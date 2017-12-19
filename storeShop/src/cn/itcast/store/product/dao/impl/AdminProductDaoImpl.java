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
     * 查询所有分类信息
     */
    @Override
    public List<Category> findAllCategory() {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "select * from category";
            // 3.设置实际参数
            Object[] params = {};
            // 4.执行查询操作
            return qr.query(sql, new BeanListHandler<>(Category.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加分类信息
     */
    @Override
    public int addCategory(Category category) {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "insert into category values(?,?)";
            // 3.设置实际参数
            Object[] params = { category.getCid(), category.getCname() };
            // 4.执行添加操作
            return qr.update(sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据cid删除分类信息
     */
    @Override
    public int delCategoryByCid(String cid) {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "delete from category where cid=?";
            // 3.设置实际参数
            Object[] params = { cid };
            // 4.执行删除操作
            return qr.update(sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据cid查询分类信息
     */
    @Override
    public Category findCategoryByCid(String cid) {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "select * from category where cid=?";
            // 3.设置实际参数
            Object[] params = { cid };
            // 4.执行查询操作
            return qr.query(sql, new BeanHandler<>(Category.class), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据cid修改分类信息
     */
    @Override
    public int editCategoryByCid(Category category) {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "update category set cname=? where cid=?";
            // 3.设置实际参数
            Object[] params = { category.getCname(), category.getCid() };
            // 4.执行更新操作
            return qr.update(sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
