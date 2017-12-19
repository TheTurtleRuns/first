package cn.itcast.store.utils;

import javax.servlet.http.HttpServletRequest;

import cn.itcast.store.product.domain.Cart;





/**
 * 从session中获得一个Cart对象！
 * 
 * @author Never Say Never
 * @date 2017年10月6日
 * @version V1.0
 */
public class CartUtils {

    public static Cart getCart(HttpServletRequest request) {
        // 1.从session中获得购物车对象
        Object obj = request.getSession().getAttribute("cart");
        // 2.非空判断
        if (obj == null) {
            // 说明session中没有购物车对象，创建一个新的返回
            Cart cart = new Cart();
            return cart;
        }else{
            return (Cart) obj;
        }
    }

}
