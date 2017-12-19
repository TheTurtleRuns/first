package cn.itcast.store.product.domain;

import java.io.Serializable;

public class CartItem implements Serializable {

    // 商品信息(页面传递pid查询出来)
    private Product product;

    // 购买数量(前台页面用户输入的数据)
    private int count;

    // 小计(计算得来：商城价格*购买数量)【商城价格和购买数量在当前的JavaBean中都有，那么直接可以将计算的业务代码放到当前的CartItem类中】
    private double subtotal;

    public double getSubtotal() {
        return subtotal = this.getProduct().getShop_price() * this.getCount();
    }

    //当前这个方法可以不用！！！！(计算的操作在get方法中已完成)
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public CartItem() {

    }
}
