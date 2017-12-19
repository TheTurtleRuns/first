  package cn.itcast.store.product.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.itcast.store.product.domain.Cart;
import cn.itcast.store.product.domain.CartItem;
import cn.itcast.store.product.domain.Category;
import cn.itcast.store.product.domain.Product;
import cn.itcast.store.product.service.IProductService;
import cn.itcast.store.product.service.impl.ProductServiceImpl;
import cn.itcast.store.utils.BaseServlet;
import cn.itcast.store.utils.BeanFactory;
import cn.itcast.store.utils.CartUtils;
import cn.itcast.store.utils.JedisUtils;
import cn.itcast.store.utils.PageBean;
import redis.clients.jedis.Jedis;

public class ProductServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;

    private IProductService service = (IProductService) BeanFactory.getBean("IProductService");

    private Gson gson = new Gson();

    /**
     * 清空购物车
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String clearCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.获得购物车对象
        Cart cart = CartUtils.getCart(request);
        // 2.调用清空购物车的方法
        cart.clearCart();
        // 3.将操作完的cart对象放回session
        request.getSession().setAttribute("cart", cart);
        // 4.重定向到cart.jsp
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
        return null;
    }

    /**
     * 根据pid删除购物项
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delCartItemByPid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.获得请求参数pid
        String pid = request.getParameter("pid");
        // 2.获得购物车对象
        Cart cart = CartUtils.getCart(request);
        // 3.调用根据pid删除购物项的方法
        cart.delCartItemByPid(pid);
        // 4.将购物车放回session
        request.getSession().setAttribute("cart", cart);
        // 5.重定向到cart.jsp
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
        return null;
    }

    /**
     * 添加购物项到购物车
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String addCartItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.获得请求参数pid和count
        String pid = request.getParameter("pid");
        int count = Integer.parseInt(request.getParameter("count"));
        // 2.调用service层根据pid查询商品信息的方法
        Product product = service.findProductInfoByPid(pid);
        // 3.创建一个购物项对象
        CartItem cartItem = new CartItem();
        // 4.封装数据到购物项对象
        cartItem.setProduct(product);
        cartItem.setCount(count);
        // 5.获得购物车对象
        Cart cart = CartUtils.getCart(request);
        // 6.将带有数据的购物项对象添加到购物车中去
        cart.addCartItem(cartItem);
        // 7.将操作之后的购物车对象放回session（方便之后操作的是同一个购物车）
        request.getSession().setAttribute("cart", cart);
        // 8.重定向到cart.jsp
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
        return null;
    }

    /**
     * 根据pid查询单个商品详情信息
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findProductInfoByPid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.获得请求参数pid
        String pid = request.getParameter("pid");
        // 2.调用service层根据pid查询单个商品详情信息的方法
        Product product = service.findProductInfoByPid(pid);
        // 3.将查询结果保存域对象
        request.setAttribute("product", product);
        // 4.转发到商品详情页面product_info.jsp
        return "product_info.jsp";
    }

    /**
     * 根据cid分页查询某个分类下面所有的商品信息
     */
    public String findAllProductByCidForPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.创建一个PageBean
        PageBean<Product> pageBean = new PageBean<>();
        // 2.获得请求参数 cid pageNumber
        String cid = request.getParameter("cid");
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        // 3.将pageNumber封装到pageBean
        pageBean.setPageNumber(pageNumber);
        // 4.调用service层根据cid分页查询某个分类下面所有的商品信息的方法
        pageBean = service.findAllProductByCidForPage(cid, pageBean);
        // 5.将查询结果保存域对象
        request.setAttribute("pageBean", pageBean);
        // 6.转发到商品列表页面product_list.jsp
        return "product_list.jsp";
    }

    /**
     * 查询热门商品信息
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllHotProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.直接调用service层查询最新商品信息的方法
        List<Product> products = service.findAllHotProduct();
        // 2.将查询结果转成json数据
        String json = gson.toJson(products);
        // System.out.println(json);
        // 3.通过响应体响应
        response.getWriter().write(json);
        return null;
    }

    /**
     * 查询最新商品信息
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllNewProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.直接调用service层查询最新商品信息的方法
        List<Product> products = service.findAllNewProduct();
        // 2.将查询结果转成json数据
        String json = gson.toJson(products);
        // System.out.println(json);
        // 3.通过响应体响应
        response.getWriter().write(json);
        return null;
    }

    /**
     * 查询所有分类信息(异步方式实现，带有缓存功能)
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllCategorys(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.获得Jedis对象
        Jedis jedis = JedisUtils.getJedis();
        try {
            // 2.从Redis数据库中获得分类信息
            String categorys = jedis.get("categorys");
            // 3.非空判断
            if (categorys == null) {
                System.out.println("Redis缓存中没有数据， 查询数据库……");
                // 说明Redis数据库中没有分类信息，查询数据库
                List<Category> cts = service.findAllCategory();
                // 将查询结果转成json数据格式
                String json = gson.toJson(cts);
                // 将数据添加到redis数据库中
                jedis.set("categorys", json);
                // 响应到客户端
                response.getWriter().write(json);
            } else {
                System.out.println("Redis缓存中有数据，直接从缓存中获取数据……");
                // 直接响应
                response.getWriter().write(categorys);
            }
        } finally {
            // 释放资源
            if (jedis != null)
                jedis.close();
        }
        return null;
    }

    /**
     * 查询所有分类信息(异步方式实现)
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
   /* public String findAllCategorys(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.直接调用Service层查询所有分类信息的方法
        List<Category> categorys = service.findAllCategory();
        // 2.将查询结果转成json数据格式
        String json = gson.toJson(categorys);
        // System.out.println(json);
        // 3.通过响应体响应
        response.getWriter().write(json);
        return null;
    }*/

    /**
     * 查询所有分类信息(同步方式实现)
     * 
     * @param request
     * @param response                                  
     * @return
     * @throws ServletException
     * @throws IOException
     */
    /*public String findAllCategory0(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.直接调用Service层查询所有分类信息的方法
        List<Category> categorys = service.findAllCategory();
        // 2.将查询结果保存到域对象
        request.setAttribute("categorys", categorys);
        // 3.转发到首页
        return "index.jsp";
    }
*/
}