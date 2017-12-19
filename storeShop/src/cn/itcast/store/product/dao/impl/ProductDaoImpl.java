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
     * 根据pid查询单个商品详情信息
     */
    @Override
    public Product findProductInfoByPid(String pid) {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "select * from product where pid=?";
            // 3.设置实际参数
            Object[] params = { pid };
            // 4.执行查询操作
            return qr.query(sql, new BeanHandler<>(Product.class), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 层根据cid查询某个分类下面商品的总记录数 total
     */
    @Override
    public int getTotalByCid(String cid) {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "select count(*) from product where cid=?";
            // 3.设置实际参数
            Object[] params = { cid };
            // 4.执行查询操作
            Long count = (Long) qr.query(sql, new ScalarHandler(), params);
            return count.intValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据cid分页查询某个分类下面所有的商品信息
     */
    @Override
    public List<Product> findAllProductByCidForPage(String cid, PageBean<Product> pageBean) {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "select * from product where cid=? and pflag=? limit ?,?";
            // 3.设置实际参数
            Object[] params = { cid, 0, pageBean.getStartIndex(), pageBean.getPageSize() };
            // 4.执行查询操作
            return qr.query(sql, new BeanListHandler<>(Product.class), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询热门商品信息
     */
    @Override
    public List<Product> findAllHotProduct() {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "select * from product where pflag=? and is_hot=? order by pdate desc limit ?";
            // 3.设置实际参数
            Object[] params = { 0, 1, 9 };
            // 4.执行查询操作
            return qr.query(sql, new BeanListHandler<>(Product.class), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询最新商品信息
     */
    @Override
    public List<Product> findAllNewProduct() {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "select * from product where pflag=? order by pdate desc limit ?";
            // 3.设置实际参数
            Object[] params = { 0, 9 };
            // 4.执行查询操作
            return qr.query(sql, new BeanListHandler<>(Product.class), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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

}
