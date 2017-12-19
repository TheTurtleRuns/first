package cn.itcast.store.user.service;

import cn.itcast.store.user.domain.User;

public interface IUserService {

    int register(User user);

    User findUserInfoByCode(String code);

    int active(User user);

    User login(User user);

}
