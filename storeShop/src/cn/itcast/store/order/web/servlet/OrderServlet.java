package cn.itcast.store.order.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.itcast.store.order.domain.Order;
import cn.itcast.store.order.domain.OrderItem;
import cn.itcast.store.order.service.IOrderService;
import cn.itcast.store.order.utils.PaymentUtil;
import cn.itcast.store.product.domain.Cart;
import cn.itcast.store.product.domain.CartItem;
import cn.itcast.store.user.domain.User;
import cn.itcast.store.utils.BaseServlet;
import cn.itcast.store.utils.BeanFactory;
import cn.itcast.store.utils.CartUtils;
import cn.itcast.store.utils.PageBean;
import cn.itcast.store.utils.UUIDUtils;

public class OrderServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;

    private IOrderService service = (IOrderService) BeanFactory.getBean("IOrderService");

    /**
     * 支付成功之后修改订单的状态
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String payOrderSuccess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取返回的所有参数
        String p1_MerId = request.getParameter("p1_MerId");
        String r0_Cmd = request.getParameter("r0_Cmd");
        String r1_Code = request.getParameter("r1_Code");
        String r2_TrxId = request.getParameter("r2_TrxId");
        String r3_Amt = request.getParameter("r3_Amt");
        String r4_Cur = request.getParameter("r4_Cur");
        String r5_Pid = request.getParameter("r5_Pid");
        String r6_Order = request.getParameter("r6_Order");
        String r7_Uid = request.getParameter("r7_Uid");
        String r8_MP = request.getParameter("r8_MP");
        String r9_BType = request.getParameter("r9_BType");
        String rb_BankId = request.getParameter("rb_BankId");
        String ro_BankOrderId = request.getParameter("ro_BankOrderId");
        String rp_PayDate = request.getParameter("rp_PayDate");
        String rq_CardNo = request.getParameter("rq_CardNo");
        String ru_Trxtime = request.getParameter("ru_Trxtime");
        String hmac = request.getParameter("hmac");

        // 校验数据是否正确
        boolean flag = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur,
                r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType,
                "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
        if (flag) {
            // 数据正确,修改订单状态
            try {
                Order order = service.findOrderInfoByOid(r6_Order);
                order.setState(1);
                service.modifyOrderInfoByOid(order);
                request.setAttribute("msg", "订单付款成功,订单号为:" + r6_Order + "///付款金额为:" + r3_Amt);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            throw new RuntimeException("数据遭篡改");
        }
        return "/msg.jsp";
    }

    /**
     * 在线支付(修改订单信息)
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String payForOrderByOid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /////////////////////////////////// 修改订单信息(update)///////////////////////////////////////////////
        // 1.获得请求参数oid
        String oid = request.getParameter("oid");
        // 2.调用service层根据oid查询订单详情信息的方法
        Order order = service.findOrderInfoByOid(oid);
        try {
            // 3.使用BeanUtils封装用户修改的信息到order对象
            BeanUtils.populate(order, request.getParameterMap());
            // 4.调用service层根据oid修改订单信息的方法
            int rows = service.modifyOrderInfoByOid(order);
            // 5.判断
            if (rows > 0) {
                // 6.说明修改成功，进行在线支付！！！！
                /////////////////////////////////// 在线支付/////////////////////////////////////////////////
                String p0_Cmd = "Buy";
                String p1_MerId = "10001126856";// 已经在易宝注册过的账户
                String p2_Order = order.getOid();
                String p3_Amt = "0.01";// 测试用1分钱，真正开发中用order.getTotal();
                String p4_Cur = "CNY";
                String p5_Pid = "";
                String p6_Pcat = "";
                String p7_Pdesc = "";
                String p8_Url = "http://localhost:8080" + request.getContextPath()
                        + "/OrderServlet?method=payOrderSuccess";
                String p9_SAF = "0";
                String pa_MP = "";
                String pd_FrpId = request.getParameter("pd_FrpId");
                String pr_NeedResponse = "1";
                String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid,
                        p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse,
                        "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");

                StringBuffer buffer = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
                buffer.append("p0_Cmd=" + p0_Cmd);
                buffer.append("&p1_MerId=" + p1_MerId);
                buffer.append("&p2_Order=" + p2_Order);
                buffer.append("&p3_Amt=" + p3_Amt);
                buffer.append("&p4_Cur=" + p4_Cur);
                buffer.append("&p5_Pid=" + p5_Pid);
                buffer.append("&p6_Pcat=" + p6_Pcat);
                buffer.append("&p7_Pdesc=" + p7_Pdesc);
                buffer.append("&p8_Url=" + p8_Url);
                buffer.append("&p9_SAF=" + p9_SAF);
                buffer.append("&pa_MP=" + pa_MP);
                buffer.append("&pd_FrpId=" + pd_FrpId);
                buffer.append("&pr_NeedResponse=" + pr_NeedResponse);
                buffer.append("&hmac=" + hmac);

                response.sendRedirect(buffer.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 根据oid查询订单详情信息
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findOrderInfoByOid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.获得请求参数oid
        String oid = request.getParameter("oid");
        // 2.调用service层根据oid查询订单详情信息的方法
        Order order = service.findOrderInfoByOid(oid);
        // 3.将查询结果保存域对象
        request.setAttribute("order", order);
        // 4.转发到order_info.jsp
        return "order_info.jsp";
    }

    /**
     * 根据uid分页查询某个用户下所有的订单信息
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllOrdersByUidForPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.从session中获得用户信息
        User exsitUser = (User) request.getSession().getAttribute("exsitUser");
        // 2.非空判断
        if (exsitUser == null) {
            // 说明登录超时
            request.setAttribute("msg", "<h3 style='color:red'>哥们，你登录超时了，请重新登录后再进行查询所有订单操作……</h3>");
            return "msg.jsp";
        }
        // 3.获得请求参数pageNumber
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        // 4.创建一个PageBean
        PageBean<Order> pageBean = new PageBean<>();
        // 5.封装pageNumber
        pageBean.setPageNumber(pageNumber);
        // 6.调用service层根据uid分页查询某个用户下所有的订单信息的方法
        pageBean = service.findAllOrdersByUidForPage(exsitUser, pageBean);
        // 7.将查询结果保存域对象
        request.setAttribute("pageBean", pageBean);
        // 8.转发到order_list.jsp
        return "order_list.jsp";
    }

    /**
     * 生成订单
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String makeOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.获得用户信息
        User exsitUser = (User) request.getSession().getAttribute("exsitUser");
        // 2.非空判断
        if (exsitUser == null) {
            // 说明用户处于未登录状态，不允许生成订单
            request.setAttribute("msg", "<h3 style='color:red'>哥们，你还未登录，请登录之后再执行生成订单操作……</h3>");
            // 转发到msg.jsp页面
            return "msg.jsp";
        }
        // 3.获得购物车
        Cart cart = CartUtils.getCart(request);
        // 4.判断
        if (cart == null || cart.getMap().size() == 0) {
            // 说明购物车中没有商品，也不允许生成订单
            request.setAttribute("msg", "<h3 style='color:red'>哥们，你购物车中空空如也，请先买买买，之后再执行生成订单操作……</h3>");
            // 转发到msg.jsp页面
            return "msg.jsp";
        }
        // 5.创建一个订单对象
        Order order = new Order();
        order.setOid(UUIDUtils.getUUID());
        order.setOrdertime(new Date());
        // 根据购物车中的总金额来封装订单的总金额
        order.setTotal(cart.getTotal());
        // 新生成的订单肯定处于未付款状态
        order.setState(0);
        order.setUser(exsitUser);

        // 6.创建一个订单项集合
        ArrayList<OrderItem> orderItems = new ArrayList<>();

        // 7.遍历购物车中的购物项
        for (String key : cart.getMap().keySet()) {
            // 根据key获得单个购物项
            CartItem cartItem = cart.getMap().get(key);
            // 创建一个订单项对象
            OrderItem orderItem = new OrderItem();
            /////// 根据购物项封装订单项的数据
            orderItem.setItemid(UUIDUtils.getUUID());
            orderItem.setCount(cartItem.getCount());
            orderItem.setSubtotal(cartItem.getSubtotal());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(order);
            // 将封装好数据的订单项添加到订单项集合中去
            orderItems.add(orderItem);
        }
        // 8.将封装好数据的订单项集合对象封装到订单对象中去
        order.setList(orderItems);
        // 9.调用service层生成订单的方法(本质的是一个插入操作)
        int rows = service.makeOrder(order);
        // 10.对操作结果进行判断
        if (rows > 0) {
            // 清空购物车的数据
            // request.getSession().removeAttribute("cart");
            // 将订单信息保存到域对象
            request.setAttribute("order", order);
            // 转发到order_info.jsp页面
            return "order_info.jsp";
        } else {
            // 给出生成订单失败的提示信息
            request.setAttribute("msg", "<h3 style='color:red'>生成订单失败，请重新操作……</h3>");
            // 转发到msg.jsp页面
            return "msg.jsp";
        }
    }

}