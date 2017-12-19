package cn.itcast.store.user.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.store.user.dao.IUserDao;
import cn.itcast.store.user.domain.User;
import cn.itcast.store.utils.C3P0Utils;

public class UserDaoImpl implements IUserDao {

    /**
     * �û�ע��(�������)
     */
    @Override
    public int register(User user) {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
            // 3.����ʵ�ʲ���
            Object[] params = { user.getUid(), user.getUsername(), user.getPassword(), user.getName(),
                    user.getEmail(), null, user.getBirthday(), user.getSex(), user.getState(),
                    user.getCode() };
            // 4.ִ�в������
            return qr.update(sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ���ݼ������ѯ�û���Ϣ
     */
    @Override
    public User findUserInfoByCode(String code) {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "select * from user where code=?";
            // 3.����ʵ�ʲ���
            Object[] params = { code };
            // 4.ִ�в�ѯ����
            return qr.query(sql, new BeanHandler<>(User.class), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * �����û���Ϣ(ͨ�õĸ��·���)
     */
    @Override
    public int active(User user) {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "update user set password=?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid=?";
            // 3.����ʵ�ʲ���
            Object[] params = { user.getPassword(), user.getName(), user.getEmail(), user.getTelephone(),
                    user.getBirthday(), user.getSex(), user.getState(), user.getCode(), user.getUid() };
            // 4.ִ�и��²���
            return qr.update(sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User login(User user) {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "select * from user where username=? and password=? and state=?";
            // 3.����ʵ�ʲ���
            Object[] params = { user.getUsername(), user.getPassword(), 1 };
            // 4.ִ�в�ѯ����
            return qr.query(sql, new BeanHandler<>(User.class), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
