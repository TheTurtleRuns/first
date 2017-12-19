package cn.itcast.store.order.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import cn.itcast.store.user.domain.User;

public class Order implements Serializable {

    // ����ID(uuid)
    private String oid;

    // �µ�ʱ��(��ǰʱ��)
    private Date ordertime;

    // �ܽ��(���ݹ��ﳵ���ܽ�������ɶ������ܽ��)
    private double total;

    // ����״̬(0:δ���1���Ѹ���δ������2���ѷ���δǩ�գ�3����ǩ��δ����)�������ɵĶ����϶���δ����״̬��
    private int state;

    // �ջ��˵�ַ(�����û�����֧����ʱ�����û��Լ�����)
    private String address;

    // �ջ�������(�����û�����֧����ʱ�����û��Լ�����)
    private String name;

    // �ջ��˵绰(�����û�����֧����ʱ�����û��Լ�����)
    private String telephone;

    // ���uid(��session�л��)
    private User user;
    
    //���һ���������(List)����(����ֻ��Ϊ����ҳ��ȥ��ʾ����<���������Ʒ��Ϣ������>)
    private ArrayList<OrderItem> list = new ArrayList<>();
    
    public ArrayList<OrderItem> getList() {
        return list;
    }

    public void setList(ArrayList<OrderItem> list) {
        this.list = list;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order() {

    }
}
