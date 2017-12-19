package cn.itcast.store.user.dao;

import cn.itcast.store.user.domain.User;

public interface IUserDao {

    int register(User user);

    User findUserInfoByCode(String code);

    int active(User user);

    User login(User user);

}
