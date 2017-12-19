package cn.itcast.store.utils;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet{

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //0.获得请求参数method
        String method = request.getParameter("method");
        if(method!=null){
            try {
                //1.获得字节码对象(有三种)【this代表的是子类】
                Class clazz = this.getClass();//通过当前类的一个实例对象来获得字节码对象
                //2.创建对象(省略：this就是当前类的一个实例对象)
                //3.获得Method对象
                Method method2 = clazz.getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
                //4.执行指定的方法
                String path = (String) method2.invoke(this, request,response);
                //5.非空判断
                if(path!=null){
                    //说明需要转发
                    request.getRequestDispatcher(path).forward(request, response);
                    //不让后面的代码执行
                    return ;
                }
            } catch (Exception e) {
                System.out.println("哥们，你的method的值与定义的方法名不一致！");
                throw new RuntimeException(e);
            }
        }else{
           System.out.println("哥们，你忘记传递method参数了……"); 
        }
    }
}
