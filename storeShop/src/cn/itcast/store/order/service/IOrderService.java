package cn.itcast.store.order.service;

import cn.itcast.store.order.domain.Order;
import cn.itcast.store.user.domain.User;
import cn.itcast.store.utils.PageBean;

public interface IOrderService {

    int makeOrder(Order order);

    PageBean<Order> findAllOrdersByUidForPage(User exsitUser, PageBean<Order> pageBean);

    Order findOrderInfoByOid(String oid);

    int modifyOrderInfoByOid(Order order);

}
