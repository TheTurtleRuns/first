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
        //0.����������method
        String method = request.getParameter("method");
        if(method!=null){
            try {
                //1.����ֽ������(������)��this����������ࡿ
                Class clazz = this.getClass();//ͨ����ǰ���һ��ʵ������������ֽ������
                //2.��������(ʡ�ԣ�this���ǵ�ǰ���һ��ʵ������)
                //3.���Method����
                Method method2 = clazz.getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
                //4.ִ��ָ���ķ���
                String path = (String) method2.invoke(this, request,response);
                //5.�ǿ��ж�
                if(path!=null){
                    //˵����Ҫת��
                    request.getRequestDispatcher(path).forward(request, response);
                    //���ú���Ĵ���ִ��
                    return ;
                }
            } catch (Exception e) {
                System.out.println("���ǣ����method��ֵ�붨��ķ�������һ�£�");
                throw new RuntimeException(e);
            }
        }else{
           System.out.println("���ǣ������Ǵ���method�����ˡ���"); 
        }
    }
}
