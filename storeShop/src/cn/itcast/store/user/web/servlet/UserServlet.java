package cn.itcast.store.user.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.itcast.store.user.domain.User;
import cn.itcast.store.user.service.IUserService;
import cn.itcast.store.user.service.impl.UserServiceImpl;
import cn.itcast.store.user.utils.MailUtils;
import cn.itcast.store.utils.BaseServlet;
import cn.itcast.store.utils.BeanFactory;
import cn.itcast.store.utils.UUIDUtils;

/**
 * 用户模块
 * 
 * @author Never Say Never
 * @date 2017年9月27日
 * @version V1.0
 */
public class UserServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;

    /**
     * 面向接口编程： 1.方法后期对方法进行增强（动态代理） 2.方便多人(团队)开发
     */
    //private IUserService service = new UserServiceImpl();
    private IUserService service = (IUserService) BeanFactory.getBean("IUserService");

    /**
     * 用户注销(从session中移除数据)
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //1.移除Session中的数据
        request.getSession().removeAttribute("exsitUser");
        //2.转发到首页
        return "index.jsp";
    }

    /**
     * 用户登录(query)
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.创建User对象
        User user = new User();
        // 2.使用BeanUtils封装登录表单提交的用户名和密码数据封装到user对象
        try {
            BeanUtils.populate(user, request.getParameterMap());
            // 3.调用Service层登录的方法(本质是一个查询操作)
            User exsitUser = service.login(user);
            // 4.非空判断
            if (exsitUser != null) {
                // 说明登录成功，将用户信息保存到域对象，在页面获取用户名
                request.getSession().setAttribute("exsitUser", exsitUser);
                // 重定向首页
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                // 给出提示信息
                request.setAttribute("msg", "<div style='color:red'>用户名或密码错误或还未激活……</div>");
                // 转发到登录页面
                return "login.jsp";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 用户激活(update:先查后改)
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String active(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.获得请求参数code
        String code = request.getParameter("code");
        // 2.调用service层根据code查询用户信息的方法
        User user = service.findUserInfoByCode(code);

        if (user != null) {
            // 3.更新用户信息(设置state为1；code数据删除)
            user.setState(1);
            user.setCode("");
            // 4.调用service层激活的方法(本质是update)
            int rows = service.active(user);
            // 5.对操作结果进行判断
            if (rows > 0) {
                // 说明激活成功，让其登录
                request.setAttribute("msg", "<h3 color='blue'>恭喜您激活成功，3秒后进入登录页面……</h3>");
                // 转发到提示信息页面
                return "activeSuccess.jsp";
            }
        } else {
            // 说明激活失败，给出提示信息
            request.setAttribute("msg", "<h3 color='red'>激活失败，不再重复操作</h3>");
            // 转发到提示信息页面
            return "msg.jsp";
        }
        return null;
    }

    /**
     * 用户注册(insert)
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String register(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.创建一个User对象
        User user = new User();
        try {
            // 2.使用BeanUtils封装注册表单提交的数据到user对象
            BeanUtils.populate(user, request.getParameterMap());
            // 3.单独封装uid state code字段(属性)
            user.setUid(UUIDUtils.getUUID());
            user.setState(0);// 新注册的用户肯定处于未激活状态
            user.setCode(UUIDUtils.getUUID64());
            // 4.调用service层注册的方法(本质是一个插入操作)
            int rows = service.register(user);
            // 5.对操作结果进行判断
            if (rows > 0) {
                // 说明注册成功，发送一封激活邮件
                MailUtils.sendMail(user.getEmail(), user.getCode());
                // 给出提示信息
                request.setAttribute("msg", "<h3 style='color:blue'>恭喜您注册成功！请立即前往邮箱完成激活操作……</h3>");
                // 转发
                return "msg.jsp";
            } else {
                // 注册失败，给出提示信息
                request.setAttribute("msg", "<h3 style='color:red'>注册失败，请重新操作……</h3>");
                // 转发
                return "msg.jsp";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}