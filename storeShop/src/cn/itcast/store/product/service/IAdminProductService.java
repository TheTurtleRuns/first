package cn.itcast.store.product.service;

import java.util.List;

import cn.itcast.store.product.domain.Category;

public interface IAdminProductService {

    List<Category> findAllCategory();

    int addCategory(Category category);

    int delCategoryByCid(String cid);

    Category findCategoryByCid(String cid);

    int editCategoryByCid(Category category);

}
