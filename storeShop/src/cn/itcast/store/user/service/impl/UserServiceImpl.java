package cn.itcast.store.user.service.impl;

import cn.itcast.store.user.dao.IUserDao;
import cn.itcast.store.user.dao.impl.UserDaoImpl;
import cn.itcast.store.user.domain.User;
import cn.itcast.store.user.service.IUserService;
import cn.itcast.store.utils.BeanFactory;

public class UserServiceImpl implements IUserService {
    
    private IUserDao dao = (IUserDao) BeanFactory.getBean("IUserDao");

    @Override
    public int register(User user) {
        return dao.register(user);
    }

    @Override
    public User findUserInfoByCode(String code) {
        return dao.findUserInfoByCode(code);
    }

    @Override
    public int active(User user) {
        return dao.active(user);
    }

    @Override
    public User login(User user) {
        return dao.login(user);
    }

}
