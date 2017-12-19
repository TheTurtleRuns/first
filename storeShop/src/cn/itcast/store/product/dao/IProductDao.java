package cn.itcast.store.product.dao;

import java.util.List;

import cn.itcast.store.product.domain.Category;
import cn.itcast.store.product.domain.Product;
import cn.itcast.store.utils.PageBean;

public interface IProductDao {

    List<Category> findAllCategory();

    List<Product> findAllNewProduct();

    List<Product> findAllHotProduct();

    int getTotalByCid(String cid);

    List<Product> findAllProductByCidForPage(String cid, PageBean<Product> pageBean);

    Product findProductInfoByPid(String pid);

}
