package cn.itcast.store.order.web.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.user.domain.User;

public class PriFilter implements Filter {

    public void destroy() {

    }
    /**
     * ��ָ������Դ���й���(�ֿ����ȵ�Ȩ�޿���)
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        ///////////////////////ֻ���û����ڵ�¼״̬�����ܷ���Ŀ����Դ///////////////////////////////////
        //1.ǿת
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //2.��session�л���û���Ϣ
        User exsitUser = (User) request.getSession().getAttribute("exsitUser");
        //3.�ǿ��ж�
        if(exsitUser==null){
            //˵���û�����δ��¼״̬�����������Ŀ¼��Դ��������ʾ��Ϣ
            request.setAttribute("msg", "<h3 style='color:red'>���ǣ��㻹δ��½�����ܽ��з��ʣ����¼�ٽ��г��ԣ�</h3>");
            //ת��
            request.getRequestDispatcher("msg.jsp").forward(request, response);
            //���治��ִ��
            return ;
        }
        //˵���û����ڵ�¼״̬��ֱ�ӷ��У��������Ŀ����Դ
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {

    }

}