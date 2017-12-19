package cn.itcast.store.product.service;

import java.util.List;

import cn.itcast.store.product.domain.Category;
import cn.itcast.store.product.domain.Product;
import cn.itcast.store.utils.PageBean;

public interface IProductService {

    List<Category> findAllCategory();

    List<Product> findAllNewProduct();

    List<Product> findAllHotProduct();

    PageBean<Product> findAllProductByCidForPage(String cid, PageBean<Product> pageBean);

    Product findProductInfoByPid(String pid);

}
