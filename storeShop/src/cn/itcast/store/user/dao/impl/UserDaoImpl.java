package cn.itcast.store.user.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.store.user.dao.IUserDao;
import cn.itcast.store.user.domain.User;
import cn.itcast.store.utils.C3P0Utils;

public class UserDaoImpl implements IUserDao {

    /**
     * 用户注册(插入操作)
     */
    @Override
    public int register(User user) {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
            // 3.设置实际参数
            Object[] params = { user.getUid(), user.getUsername(), user.getPassword(), user.getName(),
                    user.getEmail(), null, user.getBirthday(), user.getSex(), user.getState(),
                    user.getCode() };
            // 4.执行插入操作
            return qr.update(sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据激活码查询用户信息
     */
    @Override
    public User findUserInfoByCode(String code) {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "select * from user where code=?";
            // 3.设置实际参数
            Object[] params = { code };
            // 4.执行查询操作
            return qr.query(sql, new BeanHandler<>(User.class), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新用户信息(通用的更新方法)
     */
    @Override
    public int active(User user) {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "update user set password=?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid=?";
            // 3.设置实际参数
            Object[] params = { user.getPassword(), user.getName(), user.getEmail(), user.getTelephone(),
                    user.getBirthday(), user.getSex(), user.getState(), user.getCode(), user.getUid() };
            // 4.执行更新操作
            return qr.update(sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User login(User user) {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "select * from user where username=? and password=? and state=?";
            // 3.设置实际参数
            Object[] params = { user.getUsername(), user.getPassword(), 1 };
            // 4.执行查询操作
            return qr.query(sql, new BeanHandler<>(User.class), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
