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
     * ���ɶ���
     */
    @Override
    public void makeOrder(Order order) {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner();
            // 2.��дSQL���
            String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
            // 3.����ʵ�ʲ���
            Object[] params = { order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(),
                    null, null, null, order.getUser().getUid() };
            // 4.ִ�в������
            qr.update(C3P0Utils.getConnnection(), sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * ���ɶ�����
     */
    @Override
    public void makeOrderItem(OrderItem orderItem) {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner();
            // 2.��дSQL���
            String sql = "insert into orderitem values(?,?,?,?,?)";
            // 3.����ʵ�ʲ���
            Object[] params = { orderItem.getItemid(), orderItem.getCount(), orderItem.getSubtotal(),
                    orderItem.getProduct().getPid(), orderItem.getOrder().getOid() };
            // 4.ִ�в������
            qr.update(C3P0Utils.getConnnection(), sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ����uid��ѯ��ǰ�û����еĶ������ܼ�¼��
     */
    @Override
    public int getTotalRecordByUid(User exsitUser) {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "select count(*) from orders where uid=?";
            // 3.����ʵ�ʲ���
            Object[] params = { exsitUser.getUid() };
            // 4.ִ�в�ѯ����
            Long count = (Long) qr.query(sql, new ScalarHandler(), params);
            return count.intValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ����uid��ҳ��ѯĳ���û������еĶ�����Ϣ
     */
    @Override
    public List<Order> findAllOrdersByUidForPage(User exsitUser, PageBean<Order> pageBean) {
        try {
            ///////////////////// ����uid��ҳ��ѯ���еĶ�����Ϣ/////////////////////////////////////
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "select * from orders where uid=? limit ?,?";
            // 3.����ʵ�ʲ���
            Object[] params = { exsitUser.getUid(), pageBean.getStartIndex(), pageBean.getPageSize() };
            // 4.ִ�в�ѯ����(ֻ�ܲ�ѯ������Ϣ�����ݣ�û�ж��������Ʒ��Ϣ������)
            List<Order> orders = qr.query(sql, new BeanListHandler<>(Order.class), params);
            ///////////////////// ����oid�����еĶ��������Ʒ��Ϣ�����ݲ�ѯ����////////////////////////////
            // 5.�ǿ��ж�
            if (orders != null && orders.size() > 0) {
                // 6.����
                for (Order order : orders) {
                    // 7.��дSQL���
                    String sql1 = "select * from orderitem o,product p where o.pid=p.pid and o.oid=?";
                    // 8.ִ�в�ѯ����(map��key�����ݿ��ֶε����ƣ�map��value�Ƕ�Ӧ���ֶβ�ѯ����������)
                    List<Map<String, Object>> listmap = qr.query(sql1, new MapListHandler(), order.getOid());
                    // 9.����һ��������϶���
                    ArrayList<OrderItem> orderItems = new ArrayList<>();
                    // 10.����listmap
                    for (Map<String, Object> map : listmap) {
                        // 11.����product����
                        Product product = new Product();
                        // 12.ʹ��BeanUtils��װ������
                        BeanUtils.populate(product, map);
                        // 13.����һ�����������
                        OrderItem orderItem = new OrderItem();
                        // 14.ʹ��BeanUtils��װ������
                        BeanUtils.populate(orderItem, map);
                        // 15.����װ�����ݵ�product��װ��orderItem��ȥ
                        orderItem.setProduct(product);
                        // 16.����װ�����ݵ�orderItem��䵽orderItems������ȥ
                        orderItems.add(orderItem);
                    }
                    // 17.����װ�����ݵ�orderItems��䵽order������ȥ
                    order.setList(orderItems);
                }
            }
            return orders;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ����oid��ѯ��������������Ϣ
     */
    @Override
    public Order findOrderInfoByOid(String oid) {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "select * from orders where oid=?";
            // 3.����ʵ�ʲ���
            Object[] params = { oid };
            // 4.ִ�в�ѯ����(��ѯ�Ľ��ֻ�ж�����Ϣ��û�ж��������Ʒ��Ϣ������)
            Order order = qr.query(sql, new BeanHandler<>(Order.class), params);
            // 5.����oid��ѯ�˶��������еĶ��������Ʒ��Ϣ������
            String sql1 = "select * from orderitem o,product p where o.pid=p.pid and o.oid=?";
            // 6.ִ�в�ѯ����
            List<Map<String, Object>> listmap = qr.query(sql1, new MapListHandler(), oid);
            // 7.����һ��������϶���
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            // 8.����listmap
            for (Map<String, Object> map : listmap) {
                // 9.����product����
                Product product = new Product();
                // 10.ʹ��BeanUtils��װ����
                BeanUtils.populate(product, map);
                // 11.����orderItem����
                OrderItem orderItem = new OrderItem();
                // 12.ʹ��BeanUtils��װ����
                BeanUtils.populate(orderItem, map);
                // 13.����װ�����ݵ�product��䵽orderItem��ȥ
                orderItem.setProduct(product);
                // 14.����װ�����ݵ�orderItem��䵽orderItems������ȥ
                orderItems.add(orderItem);
            }
            // 15.����װ�����ݵĶ�����϶���orderItems��䵽order������ȥ
            order.setList(orderItems);
            return order;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ����oid�޸Ķ�����Ϣ(ͨ�õĸ��·���)
     */
    @Override
    public int modifyOrderInfoByOid(Order order) {
        try {
            // 1.���QueryRunner���Ķ���
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            // 2.��дSQL���
            String sql = "update orders set state=?,address=?,name=?,telephone=? where oid=?";
            // 3.����ʵ�ʲ���
            Object[] params = { order.getState(), order.getAddress(), order.getName(), order.getTelephone(),
                    order.getOid() };
            // 4.ִ�и��²���
            return qr.update(sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
