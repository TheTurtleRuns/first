package cn.itcast.store.order.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.dom4j.bean.BeanAttribute;

import cn.itcast.store.order.dao.IOrderDao;
import cn.itcast.store.order.domain.Order;
import cn.itcast.store.order.domain.OrderItem;
import cn.itcast.store.product.domain.Product;
import cn.itcast.store.user.domain.User;
import cn.itcast.store.utils.C3P0Utils;
import cn.itcast.store.utils.PageBean;

public class OrderDaoImpl implements IOrderDao {

    /**
     * 生成订单
     */
    @Override
    public void makeOrder(Order order) {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner();
            // 2.编写SQL语句
            String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
            // 3.设置实际参数
            Object[] params = { order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(),
                    null, null, null, order.getUser().getUid() };
            // 4.执行插入操作
            qr.update(C3P0Utils.getConnnection(), sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 生成订单项
     */
    @Override
    public void makeOrderItem(OrderItem orderItem) {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner();
            // 2.编写SQL语句
            String sql = "insert into orderitem values(?,?,?,?,?)";
            // 3.设置实际参数
            Object[] params = { orderItem.getItemid(), orderItem.getCount(), orderItem.getSubtotal(),
                    orderItem.getProduct().getPid(), orderItem.getOrder().getOid() };
            // 4.执行插入操作
            qr.update(C3P0Utils.getConnnection(), sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据uid查询当前用户所有的订单的总记录数
     */
    @Override
    public int getTotalRecordByUid(User exsitUser) {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "select count(*) from orders where uid=?";
            // 3.设置实际参数
            Object[] params = { exsitUser.getUid() };
            // 4.执行查询操作
            Long count = (Long) qr.query(sql, new ScalarHandler(), params);
            return count.intValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据uid分页查询某个用户下所有的订单信息
     */
    @Override
    public List<Order> findAllOrdersByUidForPage(User exsitUser, PageBean<Order> pageBean) {
        try {
            ///////////////////// 根据uid分页查询所有的订单信息/////////////////////////////////////
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "select * from orders where uid=? limit ?,?";
            // 3.设置实际参数
            Object[] params = { exsitUser.getUid(), pageBean.getStartIndex(), pageBean.getPageSize() };
            // 4.执行查询操作(只能查询订单信息的数据，没有订单项和商品信息的数据)
            List<Order> orders = qr.query(sql, new BeanListHandler<>(Order.class), params);
            ///////////////////// 根据oid将所有的订单项和商品信息的数据查询出来////////////////////////////
            // 5.非空判断
            if (orders != null && orders.size() > 0) {
                // 6.遍历
                for (Order order : orders) {
                    // 7.编写SQL语句
                    String sql1 = "select * from orderitem o,product p where o.pid=p.pid and o.oid=?";
                    // 8.执行查询操作(map的key是数据库字段的名称；map的value是对应该字段查询出来的数据)
                    List<Map<String, Object>> listmap = qr.query(sql1, new MapListHandler(), order.getOid());
                    // 9.定义一个订单项集合对象
                    ArrayList<OrderItem> orderItems = new ArrayList<>();
                    // 10.遍历listmap
                    for (Map<String, Object> map : listmap) {
                        // 11.创建product对象
                        Product product = new Product();
                        // 12.使用BeanUtils封装在数据
                        BeanUtils.populate(product, map);
                        // 13.创建一个订单项对象
                        OrderItem orderItem = new OrderItem();
                        // 14.使用BeanUtils封装在数据
                        BeanUtils.populate(orderItem, map);
                        // 15.将封装好数据的product封装到orderItem中去
                        orderItem.setProduct(product);
                        // 16.将封装好数据的orderItem填充到orderItems集合中去
                        orderItems.add(orderItem);
                    }
                    // 17.将封装好数据的orderItems填充到order对象中去
                    order.setList(orderItems);
                }
            }
            return orders;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据oid查询单个订单详情信息
     */
    @Override
    public Order findOrderInfoByOid(String oid) {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "select * from orders where oid=?";
            // 3.设置实际参数
            Object[] params = { oid };
            // 4.执行查询操作(查询的结果只有订单信息，没有订单项和商品信息的数据)
            Order order = qr.query(sql, new BeanHandler<>(Order.class), params);
            // 5.根据oid查询此订单下所有的订单项和商品信息的数据
            String sql1 = "select * from orderitem o,product p where o.pid=p.pid and o.oid=?";
            // 6.执行查询操作
            List<Map<String, Object>> listmap = qr.query(sql1, new MapListHandler(), oid);
            // 7.创建一个订单项集合对象
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            // 8.遍历listmap
            for (Map<String, Object> map : listmap) {
                // 9.创建product对象
                Product product = new Product();
                // 10.使用BeanUtils封装数据
                BeanUtils.populate(product, map);
                // 11.创建orderItem对象
                OrderItem orderItem = new OrderItem();
                // 12.使用BeanUtils封装数据
                BeanUtils.populate(orderItem, map);
                // 13.将封装好数据的product填充到orderItem中去
                orderItem.setProduct(product);
                // 14.将封装好数据的orderItem填充到orderItems集合中去
                orderItems.add(orderItem);
            }
            // 15.将封装好数据的订单项集合对象orderItems填充到order对象中去
            order.setList(orderItems);
            return order;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据oid修改订单信息(通用的更新方法)
     */
    @Override
    public int modifyOrderInfoByOid(Order order) {
        try {
            // 1.获得QueryRunner核心对象
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.编写SQL语句
            String sql = "update orders set state=?,address=?,name=?,telephone=? where oid=?";
            // 3.设置实际参数
            Object[] params = { order.getState(), order.getAddress(), order.getName(), order.getTelephone(),
                    order.getOid() };
            // 4.执行更新操作
            return qr.update(sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
