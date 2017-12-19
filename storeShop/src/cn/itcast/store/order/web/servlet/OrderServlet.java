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
     * ֧���ɹ�֮���޸Ķ�����״̬
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String payOrderSuccess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ��ȡ���ص����в���
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

        // У�������Ƿ���ȷ
        boolean flag = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur,
                r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType,
                "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
        if (flag) {
            // ������ȷ,�޸Ķ���״̬
            try {
                Order order = service.findOrderInfoByOid(r6_Order);
                order.setState(1);
                service.modifyOrderInfoByOid(order);
                request.setAttribute("msg", "��������ɹ�,������Ϊ:" + r6_Order + "///������Ϊ:" + r3_Amt);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            throw new RuntimeException("������۸�");
        }
        return "/msg.jsp";
    }

    /**
     * ����֧��(�޸Ķ�����Ϣ)
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String payForOrderByOid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /////////////////////////////////// �޸Ķ�����Ϣ(update)///////////////////////////////////////////////
        // 1.����������oid
        String oid = request.getParameter("oid");
        // 2.����service�����oid��ѯ����������Ϣ�ķ���
        Order order = service.findOrderInfoByOid(oid);
        try {
            // 3.ʹ��BeanUtils��װ�û��޸ĵ���Ϣ��order����
            BeanUtils.populate(order, request.getParameterMap());
            // 4.����service�����oid�޸Ķ�����Ϣ�ķ���
            int rows = service.modifyOrderInfoByOid(order);
            // 5.�ж�
            if (rows > 0) {
                // 6.˵���޸ĳɹ�����������֧����������
                /////////////////////////////////// ����֧��/////////////////////////////////////////////////
                String p0_Cmd = "Buy";
                String p1_MerId = "10001126856";// �Ѿ����ױ�ע������˻�
                String p2_Order = order.getOid();
                String p3_Amt = "0.01";// ������1��Ǯ��������������order.getTotal();
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
     * ����oid��ѯ����������Ϣ
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findOrderInfoByOid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.����������oid
        String oid = request.getParameter("oid");
        // 2.����service�����oid��ѯ����������Ϣ�ķ���
        Order order = service.findOrderInfoByOid(oid);
        // 3.����ѯ������������
        request.setAttribute("order", order);
        // 4.ת����order_info.jsp
        return "order_info.jsp";
    }

    /**
     * ����uid��ҳ��ѯĳ���û������еĶ�����Ϣ
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllOrdersByUidForPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.��session�л���û���Ϣ
        User exsitUser = (User) request.getSession().getAttribute("exsitUser");
        // 2.�ǿ��ж�
        if (exsitUser == null) {
            // ˵����¼��ʱ
            request.setAttribute("msg", "<h3 style='color:red'>���ǣ����¼��ʱ�ˣ������µ�¼���ٽ��в�ѯ���ж�����������</h3>");
            return "msg.jsp";
        }
        // 3.����������pageNumber
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        // 4.����һ��PageBean
        PageBean<Order> pageBean = new PageBean<>();
        // 5.��װpageNumber
        pageBean.setPageNumber(pageNumber);
        // 6.����service�����uid��ҳ��ѯĳ���û������еĶ�����Ϣ�ķ���
        pageBean = service.findAllOrdersByUidForPage(exsitUser, pageBean);
        // 7.����ѯ������������
        request.setAttribute("pageBean", pageBean);
        // 8.ת����order_list.jsp
        return "order_list.jsp";
    }

    /**
     * ���ɶ���
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String makeOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.����û���Ϣ
        User exsitUser = (User) request.getSession().getAttribute("exsitUser");
        // 2.�ǿ��ж�
        if (exsitUser == null) {
            // ˵���û�����δ��¼״̬�����������ɶ���
            request.setAttribute("msg", "<h3 style='color:red'>���ǣ��㻹δ��¼�����¼֮����ִ�����ɶ�����������</h3>");
            // ת����msg.jspҳ��
            return "msg.jsp";
        }
        // 3.��ù��ﳵ
        Cart cart = CartUtils.getCart(request);
        // 4.�ж�
        if (cart == null || cart.getMap().size() == 0) {
            // ˵�����ﳵ��û����Ʒ��Ҳ���������ɶ���
            request.setAttribute("msg", "<h3 style='color:red'>���ǣ��㹺�ﳵ�пտ���Ҳ������������֮����ִ�����ɶ�����������</h3>");
            // ת����msg.jspҳ��
            return "msg.jsp";
        }
        // 5.����һ����������
        Order order = new Order();
        order.setOid(UUIDUtils.getUUID());
        order.setOrdertime(new Date());
        // ���ݹ��ﳵ�е��ܽ������װ�������ܽ��
        order.setTotal(cart.getTotal());
        // �����ɵĶ����϶�����δ����״̬
        order.setState(0);
        order.setUser(exsitUser);

        // 6.����һ���������
        ArrayList<OrderItem> orderItems = new ArrayList<>();

        // 7.�������ﳵ�еĹ�����
        for (String key : cart.getMap().keySet()) {
            // ����key��õ���������
            CartItem cartItem = cart.getMap().get(key);
            // ����һ�����������
            OrderItem orderItem = new OrderItem();
            /////// ���ݹ������װ�����������
            orderItem.setItemid(UUIDUtils.getUUID());
            orderItem.setCount(cartItem.getCount());
            orderItem.setSubtotal(cartItem.getSubtotal());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(order);
            // ����װ�����ݵĶ�������ӵ����������ȥ
            orderItems.add(orderItem);
        }
        // 8.����װ�����ݵĶ�����϶����װ������������ȥ
        order.setList(orderItems);
        // 9.����service�����ɶ����ķ���(���ʵ���һ���������)
        int rows = service.makeOrder(order);
        // 10.�Բ�����������ж�
        if (rows > 0) {
            // ��չ��ﳵ������
            // request.getSession().removeAttribute("cart");
            // ��������Ϣ���浽�����
            request.setAttribute("order", order);
            // ת����order_info.jspҳ��
            return "order_info.jsp";
        } else {
            // �������ɶ���ʧ�ܵ���ʾ��Ϣ
            request.setAttribute("msg", "<h3 style='color:red'>���ɶ���ʧ�ܣ������²�������</h3>");
            // ת����msg.jspҳ��
            return "msg.jsp";
        }
    }

}