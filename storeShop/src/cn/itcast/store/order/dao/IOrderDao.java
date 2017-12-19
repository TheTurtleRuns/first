package cn.itcast.store.order.dao;

import java.util.List;

import cn.itcast.store.order.domain.Order;
import cn.itcast.store.order.domain.OrderItem;
import cn.itcast.store.user.domain.User;
import cn.itcast.store.utils.PageBean;

public interface IOrderDao {

    void makeOrder(Order order);

    void makeOrderItem(OrderItem orderItem);

    int getTotalRecordByUid(User exsitUser);

    List<Order> findAllOrdersByUidForPage(User exsitUser, PageBean<Order> pageBean);

    Order findOrderInfoByOid(String oid);

    int modifyOrderInfoByOid(Order order);

}
