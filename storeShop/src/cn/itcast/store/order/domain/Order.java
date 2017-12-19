package cn.itcast.store.order.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import cn.itcast.store.user.domain.User;

public class Order implements Serializable {

    // 主键ID(uuid)
    private String oid;

    // 下单时间(当前时间)
    private Date ordertime;

    // 总金额(根据购物车的总金额来生成订单的总金额)
    private double total;

    // 订单状态(0:未付款；1：已付款未发货；2：已发货未签收；3：已签收未评价)【新生成的订单肯定是未付款状态】
    private int state;

    // 收货人地址(后期用户进行支付的时候由用户自己输入)
    private String address;

    // 收货人姓名(后期用户进行支付的时候由用户自己输入)
    private String name;

    // 收货人电话(后期用户进行支付的时候由用户自己输入)
    private String telephone;

    // 外键uid(从session中获得)
    private User user;
    
    //添加一个订单项集合(List)属性(仅仅只是为了在页面去显示数据<订单项和商品信息的数据>)
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
