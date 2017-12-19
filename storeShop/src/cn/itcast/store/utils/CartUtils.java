package cn.itcast.store.utils;

import javax.servlet.http.HttpServletRequest;

import cn.itcast.store.product.domain.Cart;





/**
 * ��session�л��һ��Cart����
 * 
 * @author Never Say Never
 * @date 2017��10��6��
 * @version V1.0
 */
public class CartUtils {

    public static Cart getCart(HttpServletRequest request) {
        // 1.��session�л�ù��ﳵ����
        Object obj = request.getSession().getAttribute("cart");
        // 2.�ǿ��ж�
        if (obj == null) {
            // ˵��session��û�й��ﳵ���󣬴���һ���µķ���
            Cart cart = new Cart();
            return cart;
        }else{
            return (Cart) obj;
        }
    }

}
