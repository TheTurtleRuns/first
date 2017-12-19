package cn.itcast.store.product.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;

import cn.itcast.store.product.domain.Category;
import cn.itcast.store.product.service.IAdminProductService;
import cn.itcast.store.product.service.impl.AdminProductServiceImpl;
import cn.itcast.store.utils.BaseServlet;
import cn.itcast.store.utils.BeanFactory;
import cn.itcast.store.utils.UUIDUtils;

/**
 * ��̨ϵͳ����ģ��
 * 
 * @author Never Say Never
 * @date 2017��10��8��
 * @version V1.0
 */
public class AdminProductServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;

         private IAdminProductService service=(IAdminProductService) BeanFactory.getBean("IAdminProductService");            

    private Gson gson = new Gson();

    /**
     * ����cid�޸ķ�����Ϣ
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String editCategoryByCid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //1.����Category����
        Category category = new Category();
        try {
            //2.ʹ��BeanUtils��װ�ύ�������ݵ�category����
            BeanUtils.populate(category, request.getParameterMap());
            //3.����service�����cid�޸ķ�����Ϣ�ķ���
            int rows = service.editCategoryByCid(category);
            //4.�ж�
            if(rows>0){
                //5.����һ���޸ķ�����Ϣ�ɹ��ı����Ϣ
                response.getWriter().write("yes");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * ����cid��ѯ������Ϣ
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findCategoryByCid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.����������cid
        String cid = request.getParameter("cid");
        // 2.����service�����cid��ѯ������Ϣ�ķ���
        Category category = service.findCategoryByCid(cid);
        // 3.����ѯ���ת��json����
        String json = gson.toJson(category);
        System.out.println(json);
        // 4.��Ӧ����
        response.getWriter().write(json);
        return null;
    }

    /**
     * ����cidɾ��������Ϣ
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delCategoryByCid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.����������cid
        String cid = request.getParameter("cid");
        // 2.����service�����cidɾ��������Ϣ�ķ���
        int rows = service.delCategoryByCid(cid);
        // 3.�ж�
        if (rows > 0) {
            // 4.����һ��ɾ��������Ϣ�ɹ��ı����Ϣ
            response.getWriter().write("yes");
        }
        return null;
    }

    /**
     * ��ӷ�����Ϣ
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String addCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.����һ��Category����
        Category category = new Category();
        // 2.����������cname
        String cname = request.getParameter("cname");
        // 3.��װ������
        category.setCname(cname);
        category.setCid(UUIDUtils.getUUID());
        // 4.����service����ӷ�����Ϣ�ķ���
        int rows = service.addCategory(category);
        // 5.�ж�
        if (rows > 0) {
            // 6.��Ӧһ����ӷ�����Ϣ�ɹ��ı��
            response.getWriter().write("yes");
        }
        return null;
    }

    /**
     * ��ѯ���з�����Ϣ
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.ֱ�ӵ���service���ѯ���з���ķ���
        List<Category> categorys = service.findAllCategory();
        // 2.����ѯ�Ľ��ת��json����
        String json = gson.toJson(categorys);
        // System.out.println(json);
        // 3.��Ӧjson����
        response.getWriter().write(json);
        return null;
    }

}