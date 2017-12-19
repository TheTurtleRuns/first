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
 * �û�ģ��
 * 
 * @author Never Say Never
 * @date 2017��9��27��
 * @version V1.0
 */
public class UserServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;

    /**
     * ����ӿڱ�̣� 1.�������ڶԷ���������ǿ����̬���� 2.�������(�Ŷ�)����
     */
    //private IUserService service = new UserServiceImpl();
    private IUserService service = (IUserService) BeanFactory.getBean("IUserService");

    /**
     * �û�ע��(��session���Ƴ�����)
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //1.�Ƴ�Session�е�����
        request.getSession().removeAttribute("exsitUser");
        //2.ת������ҳ
        return "index.jsp";
    }

    /**
     * �û���¼(query)
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.����User����
        User user = new User();
        // 2.ʹ��BeanUtils��װ��¼���ύ���û������������ݷ�װ��user����
        try {
            BeanUtils.populate(user, request.getParameterMap());
            // 3.����Service���¼�ķ���(������һ����ѯ����)
            User exsitUser = service.login(user);
            // 4.�ǿ��ж�
            if (exsitUser != null) {
                // ˵����¼�ɹ������û���Ϣ���浽�������ҳ���ȡ�û���
                request.getSession().setAttribute("exsitUser", exsitUser);
                // �ض�����ҳ
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                // ������ʾ��Ϣ
                request.setAttribute("msg", "<div style='color:red'>�û�������������δ�����</div>");
                // ת������¼ҳ��
                return "login.jsp";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * �û�����(update:�Ȳ���)
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String active(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.����������code
        String code = request.getParameter("code");
        // 2.����service�����code��ѯ�û���Ϣ�ķ���
        User user = service.findUserInfoByCode(code);

        if (user != null) {
            // 3.�����û���Ϣ(����stateΪ1��code����ɾ��)
            user.setState(1);
            user.setCode("");
            // 4.����service�㼤��ķ���(������update)
            int rows = service.active(user);
            // 5.�Բ�����������ж�
            if (rows > 0) {
                // ˵������ɹ��������¼
                request.setAttribute("msg", "<h3 color='blue'>��ϲ������ɹ���3�������¼ҳ�桭��</h3>");
                // ת������ʾ��Ϣҳ��
                return "activeSuccess.jsp";
            }
        } else {
            // ˵������ʧ�ܣ�������ʾ��Ϣ
            request.setAttribute("msg", "<h3 color='red'>����ʧ�ܣ������ظ�����</h3>");
            // ת������ʾ��Ϣҳ��
            return "msg.jsp";
        }
        return null;
    }

    /**
     * �û�ע��(insert)
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String register(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.����һ��User����
        User user = new User();
        try {
            // 2.ʹ��BeanUtils��װע����ύ�����ݵ�user����
            BeanUtils.populate(user, request.getParameterMap());
            // 3.������װuid state code�ֶ�(����)
            user.setUid(UUIDUtils.getUUID());
            user.setState(0);// ��ע����û��϶�����δ����״̬
            user.setCode(UUIDUtils.getUUID64());
            // 4.����service��ע��ķ���(������һ���������)
            int rows = service.register(user);
            // 5.�Բ�����������ж�
            if (rows > 0) {
                // ˵��ע��ɹ�������һ�⼤���ʼ�
                MailUtils.sendMail(user.getEmail(), user.getCode());
                // ������ʾ��Ϣ
                request.setAttribute("msg", "<h3 style='color:blue'>��ϲ��ע��ɹ���������ǰ��������ɼ����������</h3>");
                // ת��
                return "msg.jsp";
            } else {
                // ע��ʧ�ܣ�������ʾ��Ϣ
                request.setAttribute("msg", "<h3 style='color:red'>ע��ʧ�ܣ������²�������</h3>");
                // ת��
                return "msg.jsp";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}