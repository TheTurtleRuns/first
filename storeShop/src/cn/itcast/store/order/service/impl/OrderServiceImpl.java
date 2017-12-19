package cn.itcast.store.order.service.impl;

import java.sql.Connection;
import java.util.List;

import cn.itcast.store.order.dao.IOrderDao;
import cn.itcast.store.order.domain.Order;
import cn.itcast.store.order.domain.OrderItem;
import cn.itcast.store.order.service.IOrderService;
import cn.itcast.store.user.domain.User;
import cn.itcast.store.utils.BeanFactory;
import cn.itcast.store.utils.C3P0Utils;
import cn.itcast.store.utils.PageBean;

public class OrderServiceImpl implements IOrderService {

    // private IOrderDao dao = new OrderDaoImpl();

    // 使用工厂来解耦和
    private IOrderDao dao = (IOrderDao) BeanFactory.getBean("IOrderDao");

    /**
     * 生成订单(需要使用事务控制，保证订单和订单项同时操作成功)
     */
    @Override
    public int makeOrder(Order order) {
        // 1.获得Connection对象
        Connection conn = C3P0Utils.getConnnection();
        try {
            // 2.设置手动提交
            conn.setAutoCommit(false);
            // 3.调用dao层生成订单的方法
            dao.makeOrder(order);
            // 4.遍历订单对象得到当个的订单项
            for (OrderItem orderItem : order.getList()) {
                // 调用dao层生成订单项的方法
                dao.makeOrderItem(orderItem);
            }
            // 5.提交事务
            conn.commit();
            return 3;
        } catch (Exception e) {
            // 6.回滚事务
            try {
                conn.rollback();
                return 0;
            } catch (Exception e2) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 根据uid分页查询某个用户下所有的订单信息
     */
    @Override
    public PageBean<Order> findAllOrdersByUidForPage(User exsitUser, PageBean<Order> pageBean) {
        
        //1.设置每页显示大小
        int pageSize = 4;
        pageBean.setPageSize(pageSize);
        
        //2.调用dao层根据uid查询当前用户所有的订单的总记录数
        int totalRecord = dao.getTotalRecordByUid(exsitUser);
        pageBean.setTotal(totalRecord);
        
        //3.调用dao层根据uid分页查询某个用户下所有的订单信息的方法
        List<Order> orders = dao.findAllOrdersByUidForPage(exsitUser,pageBean);
        pageBean.setRows(orders);
        
        return pageBean;
    }

    @Override
    public Order findOrderInfoByOid(String oid) {
        return dao.findOrderInfoByOid(oid);
    }

    @Override
    public int modifyOrderInfoByOid(Order order) {
        return dao.modifyOrderInfoByOid(order);
    }

}
