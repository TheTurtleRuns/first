package cn.itcast.store.product.service.impl;

import java.util.List;

import cn.itcast.store.product.dao.IProductDao;
import cn.itcast.store.product.dao.impl.ProductDaoImpl;
import cn.itcast.store.product.domain.Category;
import cn.itcast.store.product.domain.Product;
import cn.itcast.store.product.service.IProductService;
import cn.itcast.store.utils.BeanFactory;
import cn.itcast.store.utils.PageBean;

public class ProductServiceImpl implements IProductService {
    
    private IProductDao dao = (IProductDao) BeanFactory.getBean("IProductDao");

    /**
     * 根据cid分页查询某个分类下面所有的商品信息
     */
    @Override
    public PageBean<Product> findAllProductByCidForPage(String cid, PageBean<Product> pageBean) {

        // 1.设置每页显示大小pageSize
        int pageSize = 12;
        pageBean.setPageSize(pageSize);

        // 2.调用dao层根据cid查询某个分类下面商品的总记录数 total的方法
        int total = dao.getTotalByCid(cid);
        pageBean.setTotal(total);

        // 3.调用dao层根据cid分页查询某个分类下面所有的商品信息的方法
        List<Product> products = dao.findAllProductByCidForPage(cid, pageBean);
        pageBean.setRows(products);

        return pageBean;
    }

    @Override
    public List<Category> findAllCategory() {
        return dao.findAllCategory();
    }

    @Override
    public List<Product> findAllNewProduct() {
        return dao.findAllNewProduct();
    }

    @Override
    public List<Product> findAllHotProduct() {
        return dao.findAllHotProduct();
    }

    @Override
    public Product findProductInfoByPid(String pid) {
        return dao.findProductInfoByPid(pid);
    }

}
