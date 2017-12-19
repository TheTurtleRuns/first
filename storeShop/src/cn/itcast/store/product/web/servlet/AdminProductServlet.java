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
 * 后台系统分类模块
 * 
 * @author Never Say Never
 * @date 2017年10月8日
 * @version V1.0
 */
public class AdminProductServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;

         private IAdminProductService service=(IAdminProductService) BeanFactory.getBean("IAdminProductService");            

    private Gson gson = new Gson();

    /**
     * 根据cid修改分类信息
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String editCategoryByCid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //1.创建Category对象
        Category category = new Category();
        try {
            //2.使用BeanUtils封装提交表单的数据到category对象
            BeanUtils.populate(category, request.getParameterMap());
            //3.调用service层根据cid修改分类信息的方法
            int rows = service.editCategoryByCid(category);
            //4.判断
            if(rows>0){
                //5.给出一个修改分类信息成功的标记信息
                response.getWriter().write("yes");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 根据cid查询分类信息
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findCategoryByCid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.获得请求参数cid
        String cid = request.getParameter("cid");
        // 2.调用service层根据cid查询分类信息的方法
        Category category = service.findCategoryByCid(cid);
        // 3.将查询结果转成json数据
        String json = gson.toJson(category);
        System.out.println(json);
        // 4.响应数据
        response.getWriter().write(json);
        return null;
    }

    /**
     * 根据cid删除分类信息
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delCategoryByCid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.获得请求参数cid
        String cid = request.getParameter("cid");
        // 2.调用service层根据cid删除分类信息的方法
        int rows = service.delCategoryByCid(cid);
        // 3.判断
        if (rows > 0) {
            // 4.给出一个删除分类信息成功的标记信息
            response.getWriter().write("yes");
        }
        return null;
    }

    /**
     * 添加分类信息
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String addCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.创建一个Category对象
        Category category = new Category();
        // 2.获得请求参数cname
        String cname = request.getParameter("cname");
        // 3.封装好数据
        category.setCname(cname);
        category.setCid(UUIDUtils.getUUID());
        // 4.调用service层添加分类信息的方法
        int rows = service.addCategory(category);
        // 5.判断
        if (rows > 0) {
            // 6.响应一个添加分类信息成功的标记
            response.getWriter().write("yes");
        }
        return null;
    }

    /**
     * 查询所有分类信息
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.直接调用service层查询所有分类的方法
        List<Category> categorys = service.findAllCategory();
        // 2.将查询的结果转成json数据
        String json = gson.toJson(categorys);
        // System.out.println(json);
        // 3.响应json数据
        response.getWriter().write(json);
        return null;
    }

}