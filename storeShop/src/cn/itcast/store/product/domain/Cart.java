package cn.itcast.store.product.domain;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class Cart implements Serializable {

    // 购物项集合（为何如此选择请参考图片）【如果购物车中之前有这个购物项，那么就不用添加了，只需要更新购买数量即可！如果没有，需要向map中添加！】
    private LinkedHashMap<String, CartItem> map = new LinkedHashMap<>();

    // 总金额(用户可能将购物车中的数据全部删除了，初始值0.0)【计算得来：分三种情况<添加、删除、清空>】
    private double total = 0.0;

    /**
     * 总金额需要分三种情况来进行计算，我们单独的定义方法来实现
     * 
     * @return
     */

    // 添加购物项到购物车（购物车中之前有这个购物项，那说明是追加操作，否则是添加操作）
    public void addCartItem(CartItem cartItem) {
        // 通过用户加入购物车的购物项获得其pid
        String pid = cartItem.getProduct().getPid();
        // 在购物车中通过pid看购物项是否存在（非空判断）
        CartItem oldCartItem = map.get(pid);
        // 非空判断
        if (oldCartItem == null) {
            // 说明购物车中之前没有此购物项，是添加操作
            map.put(pid, cartItem);
        } else {
            // 说明购物车中之前有此购物项，是追加操作(更新购买数量即可)
            oldCartItem.setCount(oldCartItem.getCount() + cartItem.getCount());
        }
        // 计算总金额：无论是添加还是追加，总金额都是小计的累加【如果是追加，那么cartItem和oldCartItem是一样的】
        total += cartItem.getSubtotal();
    }

    // 删除购物项
    public void delCartItemByPid(String pid) {
        // 根据pid删除购物项
        CartItem removeCartItem = map.remove(pid);
        // 计算总金额
        total -= removeCartItem.getSubtotal();
    }

    // 清空购物车
    public void clearCart() {
        // 清空购物车
        map.clear();
        // 总金额归零
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
