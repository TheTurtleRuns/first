package cn.itcast.store.order.domain;

import java.io.Serializable;

import cn.itcast.store.product.domain.Product;

public class OrderItem implements Serializable {
    
    // 主键ID(uuid)
    private String itemid;

    // 购买数量(根据购物车中的购物项来生成)
    private int count;

    // 小计(根据购物车中的购物项来生成)
    private double subtotal;

    // 外键pid((根据购物车中的购物项来生成))
    private Product product;

    // 外键oid(根据购物车封装订单对象，从而封装订单项)
    private Order order;

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderItem() {

    }

}
