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
     * ����cid��ҳ��ѯĳ�������������е���Ʒ��Ϣ
     */
    @Override
    public PageBean<Product> findAllProductByCidForPage(String cid, PageBean<Product> pageBean) {

        // 1.����ÿҳ��ʾ��СpageSize
        int pageSize = 12;
        pageBean.setPageSize(pageSize);

        // 2.����dao�����cid��ѯĳ������������Ʒ���ܼ�¼�� total�ķ���
        int total = dao.getTotalByCid(cid);
        pageBean.setTotal(total);

        // 3.����dao�����cid��ҳ��ѯĳ�������������е���Ʒ��Ϣ�ķ���
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
