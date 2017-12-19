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
     * ��ӷ�����Ϣ(��Ҫ����Redis��������:��redis�еķ�����Ϣ������ɾ������)
     */
    @Override
    public int addCategory(Category category) {
        //1.���Jedis����
        Jedis jedis = JedisUtils.getJedis();
        try {
            //2.ɾ��redis�з�����Ϣ
            jedis.del("categorys");
        } finally {
            //3.�ͷ���Դ
            if(jedis!=null)
                jedis.close();
        }
        return dao.addCategory(category);
    }

    /**
     * ����cidɾ��������Ϣ����Ҫ����Redis��������:��redis�еķ�����Ϣ������ɾ�����ɣ�
     */
    @Override
    public int delCategoryByCid(String cid) {
        //1.���Jedis����
        Jedis jedis = JedisUtils.getJedis();
        try {
            //2.ɾ��redis�з�����Ϣ
            jedis.del("categorys");
        } finally {
            //3.�ͷ���Դ
            if(jedis!=null)
                jedis.close();
        }
        return dao.delCategoryByCid(cid);
    }

    /**
     * ����cid��ѯ������Ϣ
     */
    @Override
    public Category findCategoryByCid(String cid) {
        return dao.findCategoryByCid(cid);
    }

    /**
     * ����cid�޸ķ�����Ϣ
     */
    @Override
    public int editCategoryByCid(Category category) {
      //1.���Jedis����
        Jedis jedis = JedisUtils.getJedis();
        try {
            //2.ɾ��redis�з�����Ϣ
            jedis.del("categorys");
        } finally {
            //3.�ͷ���Դ
            if(jedis!=null)
                jedis.close();
        }
        return dao.editCategoryByCid(category);
    }

}
