package cn.itcast.store.product.service.impl;

import java.util.List;

import cn.itcast.store.product.dao.IAdminProductDao;
import cn.itcast.store.product.domain.Category;
import cn.itcast.store.product.service.IAdminProductService;
import cn.itcast.store.utils.BeanFactory;
import cn.itcast.store.utils.JedisUtils;
import redis.clients.jedis.Jedis;

public class AdminProductServiceImpl implements IAdminProductService {
    
    private IAdminProductDao dao = (IAdminProductDao) BeanFactory.getBean("IAdminProductDao");

    @Override
    public List<Category> findAllCategory() {
        return dao.findAllCategory();
    }

    /**
     * 添加分类信息(需要更新Redis缓存数据:将redis中的分类信息的数据删除即可)
     */
    @Override
    public int addCategory(Category category) {
        //1.获得Jedis对象
        Jedis jedis = JedisUtils.getJedis();
        try {
            //2.删除redis中分类信息
            jedis.del("categorys");
        } finally {
            //3.释放资源
            if(jedis!=null)
                jedis.close();
        }
        return dao.addCategory(category);
    }

    /**
     * 根据cid删除分类信息（需要更新Redis缓存数据:将redis中的分类信息的数据删除即可）
     */
    @Override
    public int delCategoryByCid(String cid) {
        //1.获得Jedis对象
        Jedis jedis = JedisUtils.getJedis();
        try {
            //2.删除redis中分类信息
            jedis.del("categorys");
        } finally {
            //3.释放资源
            if(jedis!=null)
                jedis.close();
        }
        return dao.delCategoryByCid(cid);
    }

    /**
     * 根据cid查询分类信息
     */
    @Override
    public Category findCategoryByCid(String cid) {
        return dao.findCategoryByCid(cid);
    }

    /**
     * 根据cid修改分类信息
     */
    @Override
    public int editCategoryByCid(Category category) {
      //1.获得Jedis对象
        Jedis jedis = JedisUtils.getJedis();
        try {
            //2.删除redis中分类信息
            jedis.del("categorys");
        } finally {
            //3.释放资源
            if(jedis!=null)
                jedis.close();
        }
        return dao.editCategoryByCid(category);
    }

}
