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

    // ʹ�ù����������
    private IOrderDao dao = (IOrderDao) BeanFactory.getBean("IOrderDao");

    /**
     * ���ɶ���(��Ҫʹ��������ƣ���֤�����Ͷ�����ͬʱ�����ɹ�)
     */
    @Override
    public int makeOrder(Order order) {
        // 1.���Connection����
        Connection conn = C3P0Utils.getConnnection();
        try {
            // 2.�����ֶ��ύ
            conn.setAutoCommit(false);
            // 3.����dao�����ɶ����ķ���
            dao.makeOrder(order);
            // 4.������������õ������Ķ�����
            for (OrderItem orderItem : order.getList()) {
                // ����dao�����ɶ�����ķ���
                dao.makeOrderItem(orderItem);
            }
            // 5.�ύ����
            conn.commit();
            return 3;
        } catch (Exception e) {
            // 6.�ع�����
            try {
                conn.rollback();
                return 0;
            } catch (Exception e2) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * ����uid��ҳ��ѯĳ���û������еĶ�����Ϣ
     */
    @Override
    public PageBean<Order> findAllOrdersByUidForPage(User exsitUser, PageBean<Order> pageBean) {
        
        //1.����ÿҳ��ʾ��С
        int pageSize = 4;
        pageBean.setPageSize(pageSize);
        
        //2.����dao�����uid��ѯ��ǰ�û����еĶ������ܼ�¼��
        int totalRecord = dao.getTotalRecordByUid(exsitUser);
        pageBean.setTotal(totalRecord);
        
        //3.����dao�����uid��ҳ��ѯĳ���û������еĶ�����Ϣ�ķ���
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
