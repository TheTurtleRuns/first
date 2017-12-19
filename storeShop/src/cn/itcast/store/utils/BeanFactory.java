package cn.itcast.store.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 获取bean的工厂
 * 
 * @author Never Say Never
 * @date 2017年3月8日
 * @version V1.0
 */
public class BeanFactory {

    //id：IOrderDao
    public static Object getBean(String id) {
        try {
            // 1.获取document对象
            Document doc = new SAXReader()
                    .read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));

            // 2.调用api selectSingleNode(表达式)  获得有id属性并且值为IOrderDao的bean元素节点  ：<bean id="IOrderDao" class="cn.itcast.store.order.dao.impl.OrderDaoImpl"/>
            Element beanEle = (Element) doc.selectSingleNode("//bean[@id='" + id + "']");

            // 3.获取元素的class属性
            String classValue = beanEle.attributeValue("class");

            // 4.通过反射返回实现类的对象
            Object newInstance = Class.forName(classValue).newInstance();
            return newInstance;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取bean失败");
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getBean("IProductDao"));
    }
}
