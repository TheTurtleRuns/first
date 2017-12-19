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
     * 对指定的资源进行过滤(粗颗粒度的权限控制)
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        ///////////////////////只有用户处于登录状态，才能访问目标资源///////////////////////////////////
        //1.强转
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //2.从session中获得用户信息
        User exsitUser = (User) request.getSession().getAttribute("exsitUser");
        //3.非空判断
        if(exsitUser==null){
            //说明用户处于未登录状态，不让其访问目录资源，给出提示信息
            request.setAttribute("msg", "<h3 style='color:red'>哥们，你还未登陆，不能进行访问，请登录再进行尝试！</h3>");
            //转发
            request.getRequestDispatcher("msg.jsp").forward(request, response);
            //后面不让执行
            return ;
        }
        //说明用户处于登录状态，直接放行，让其访问目标资源
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {

    }

}