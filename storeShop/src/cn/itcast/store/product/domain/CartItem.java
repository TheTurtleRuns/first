package cn.itcast.store.product.domain;

import java.io.Serializable;

public class CartItem implements Serializable {

    // ��Ʒ��Ϣ(ҳ�洫��pid��ѯ����)
    private Product product;

    // ��������(ǰ̨ҳ���û����������)
    private int count;

    // С��(����������̳Ǽ۸�*��������)���̳Ǽ۸�͹��������ڵ�ǰ��JavaBean�ж��У���ôֱ�ӿ��Խ������ҵ�����ŵ���ǰ��CartItem���С�
    private double subtotal;

    public double getSubtotal() {
        return subtotal = this.getProduct().getShop_price() * this.getCount();
    }

    //��ǰ����������Բ��ã�������(����Ĳ�����get�����������)
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
