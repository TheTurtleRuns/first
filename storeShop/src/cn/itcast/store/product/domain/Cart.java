package cn.itcast.store.product.domain;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class Cart implements Serializable {

    // ������ϣ�Ϊ�����ѡ����ο�ͼƬ����������ﳵ��֮ǰ������������ô�Ͳ�������ˣ�ֻ��Ҫ���¹����������ɣ����û�У���Ҫ��map����ӣ���
    private LinkedHashMap<String, CartItem> map = new LinkedHashMap<>();

    // �ܽ��(�û����ܽ����ﳵ�е�����ȫ��ɾ���ˣ���ʼֵ0.0)��������������������<��ӡ�ɾ�������>��
    private double total = 0.0;

    /**
     * �ܽ����Ҫ��������������м��㣬���ǵ����Ķ��巽����ʵ��
     * 
     * @return
     */

    // ��ӹ�������ﳵ�����ﳵ��֮ǰ������������˵����׷�Ӳ�������������Ӳ�����
    public void addCartItem(CartItem cartItem) {
        // ͨ���û����빺�ﳵ�Ĺ���������pid
        String pid = cartItem.getProduct().getPid();
        // �ڹ��ﳵ��ͨ��pid���������Ƿ���ڣ��ǿ��жϣ�
        CartItem oldCartItem = map.get(pid);
        // �ǿ��ж�
        if (oldCartItem == null) {
            // ˵�����ﳵ��֮ǰû�д˹��������Ӳ���
            map.put(pid, cartItem);
        } else {
            // ˵�����ﳵ��֮ǰ�д˹������׷�Ӳ���(���¹�����������)
            oldCartItem.setCount(oldCartItem.getCount() + cartItem.getCount());
        }
        // �����ܽ���������ӻ���׷�ӣ��ܽ���С�Ƶ��ۼӡ������׷�ӣ���ôcartItem��oldCartItem��һ���ġ�
        total += cartItem.getSubtotal();
    }

    // ɾ��������
    public void delCartItemByPid(String pid) {
        // ����pidɾ��������
        CartItem removeCartItem = map.remove(pid);
        // �����ܽ��
        total -= removeCartItem.getSubtotal();
    }

    // ��չ��ﳵ
    public void clearCart() {
        // ��չ��ﳵ
        map.clear();
        // �ܽ�����
        total = 0.0;
    }

    public LinkedHashMap<String, CartItem> getMap() {
        return map;
    }

    public void setMap(LinkedHashMap<String, CartItem> map) {
        this.map = map;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Cart() {

    }

}
