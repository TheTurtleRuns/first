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
     * ��չ��ﳵ
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String clearCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.��ù��ﳵ����
        Cart cart = CartUtils.getCart(request);
        // 2.������չ��ﳵ�ķ���
        cart.clearCart();
        // 3.���������cart����Ż�session
        request.getSession().setAttribute("cart", cart);
        // 4.�ض���cart.jsp
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
        return null;
    }

    /**
     * ����pidɾ��������
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delCartItemByPid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.����������pid
        String pid = request.getParameter("pid");
        // 2.��ù��ﳵ����
        Cart cart = CartUtils.getCart(request);
        // 3.���ø���pidɾ��������ķ���
        cart.delCartItemByPid(pid);
        // 4.�����ﳵ�Ż�session
        request.getSession().setAttribute("cart", cart);
        // 5.�ض���cart.jsp
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
        return null;
    }

    /**
     * ��ӹ�������ﳵ
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String addCartItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.����������pid��count
        String pid = request.getParameter("pid");
        int count = Integer.parseInt(request.getParameter("count"));
        // 2.����service�����pid��ѯ��Ʒ��Ϣ�ķ���
        Product product = service.findProductInfoByPid(pid);
        // 3.����һ�����������
        CartItem cartItem = new CartItem();
        // 4.��װ���ݵ����������
        cartItem.setProduct(product);
        cartItem.setCount(count);
        // 5.��ù��ﳵ����
        Cart cart = CartUtils.getCart(request);
        // 6.���������ݵĹ����������ӵ����ﳵ��ȥ
        cart.addCartItem(cartItem);
        // 7.������֮��Ĺ��ﳵ����Ż�session������֮���������ͬһ�����ﳵ��
        request.getSession().setAttribute("cart", cart);
        // 8.�ض���cart.jsp
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
        return null;
    }

    /**
     * ����pid��ѯ������Ʒ������Ϣ
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findProductInfoByPid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.����������pid
        String pid = request.getParameter("pid");
        // 2.����service�����pid��ѯ������Ʒ������Ϣ�ķ���
        Product product = service.findProductInfoByPid(pid);
        // 3.����ѯ������������
        request.setAttribute("product", product);
        // 4.ת������Ʒ����ҳ��product_info.jsp
        return "product_info.jsp";
    }

    /**
     * ����cid��ҳ��ѯĳ�������������е���Ʒ��Ϣ
     */
    public String findAllProductByCidForPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.����һ��PageBean
        PageBean<Product> pageBean = new PageBean<>();
        // 2.���������� cid pageNumber
        String cid = request.getParameter("cid");
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        // 3.��pageNumber��װ��pageBean
        pageBean.setPageNumber(pageNumber);
        // 4.����service�����cid��ҳ��ѯĳ�������������е���Ʒ��Ϣ�ķ���
        pageBean = service.findAllProductByCidForPage(cid, pageBean);
        // 5.����ѯ������������
        request.setAttribute("pageBean", pageBean);
        // 6.ת������Ʒ�б�ҳ��product_list.jsp
        return "product_list.jsp";
    }

    /**
     * ��ѯ������Ʒ��Ϣ
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllHotProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.ֱ�ӵ���service���ѯ������Ʒ��Ϣ�ķ���
        List<Product> products = service.findAllHotProduct();
        // 2.����ѯ���ת��json����
        String json = gson.toJson(products);
        // System.out.println(json);
        // 3.ͨ����Ӧ����Ӧ
        response.getWriter().write(json);
        return null;
    }

    /**
     * ��ѯ������Ʒ��Ϣ
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllNewProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.ֱ�ӵ���service���ѯ������Ʒ��Ϣ�ķ���
        List<Product> products = service.findAllNewProduct();
        // 2.����ѯ���ת��json����
        String json = gson.toJson(products);
        // System.out.println(json);
        // 3.ͨ����Ӧ����Ӧ
        response.getWriter().write(json);
        return null;
    }

    /**
     * ��ѯ���з�����Ϣ(�첽��ʽʵ�֣����л��湦��)
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllCategorys(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.���Jedis����
        Jedis jedis = JedisUtils.getJedis();
        try {
            // 2.��Redis���ݿ��л�÷�����Ϣ
            String categorys = jedis.get("categorys");
            // 3.�ǿ��ж�
            if (categorys == null) {
                System.out.println("Redis������û�����ݣ� ��ѯ���ݿ⡭��");
                // ˵��Redis���ݿ���û�з�����Ϣ����ѯ���ݿ�
                List<Category> cts = service.findAllCategory();
                // ����ѯ���ת��json���ݸ�ʽ
                String json = gson.toJson(cts);
                // ��������ӵ�redis���ݿ���
                jedis.set("categorys", json);
                // ��Ӧ���ͻ���
                response.getWriter().write(json);
            } else {
                System.out.println("Redis�����������ݣ�ֱ�Ӵӻ����л�ȡ���ݡ���");
                // ֱ����Ӧ
                response.getWriter().write(categorys);
            }
        } finally {
            // �ͷ���Դ
            if (jedis != null)
                jedis.close();
        }
        return null;
    }

    /**
     * ��ѯ���з�����Ϣ(�첽��ʽʵ��)
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
   /* public String findAllCategorys(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.ֱ�ӵ���Service���ѯ���з�����Ϣ�ķ���
        List<Category> categorys = service.findAllCategory();
        // 2.����ѯ���ת��json���ݸ�ʽ
        String json = gson.toJson(categorys);
        // System.out.println(json);
        // 3.ͨ����Ӧ����Ӧ
        response.getWriter().write(json);
        return null;
    }*/

    /**
     * ��ѯ���з�����Ϣ(ͬ����ʽʵ��)
     * 
     * @param request
     * @param response                                  
     * @return
     * @throws ServletException
     * @throws IOException
     */
    /*public String findAllCategory0(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.ֱ�ӵ���Service���ѯ���з�����Ϣ�ķ���
        List<Category> categorys = service.findAllCategory();
        // 2.����ѯ������浽�����
        request.setAttribute("categorys", categorys);
        // 3.ת������ҳ
        return "index.jsp";
    }
*/
}