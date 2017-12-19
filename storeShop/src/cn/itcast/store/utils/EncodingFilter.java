package cn.itcast.store.utils;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EncodingFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// װ����ģʽʵ��

		// ��̬����ģʽʵ��
		// 1.��ñ��������(�Ѵ��ڣ�ServletRequest)
		final HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		resp.setContentType("text/html;charset=utf-8");
		// 2.�����������
		HttpServletRequest myreq = (HttpServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(),
				req.getClass().getInterfaces(), new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						// 3.��ָ������������ǿ(getParameter)
						if ("getParameter".equals(method.getName())) {
							// 4.ֻ��GET������д���
							if ("GET".equalsIgnoreCase(req.getMethod())) {
								// 5.ִ�б������������ķ���(getParameter)
								String parameter = (String) method.invoke(req, args);

								// �ǿ��ж�
								if (parameter != null) {
									// 6.�Բ������б��봦��
									parameter = new String(parameter.getBytes("iso8859-1"), "utf-8");
								}
								return parameter;
							} else if ("POST".equalsIgnoreCase(req.getMethod())) {
								req.setCharacterEncoding("utf-8");
							}
							// 7.�����������ʽ����ִ��ԭ�еķ���
							return method.invoke(req, args);
						}
						// 8.���������������ִ�У���ִ�ж�Ӧ�ķ���
						return method.invoke(req, args);
					}
				});

		// 9.����(�ó���ִ��Ŀ����ķ���)
		chain.doFilter(myreq, resp);

	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}