package cn.itcast.store.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ��ȡbean�Ĺ���
 * 
 * @author Never Say Never
 * @date 2017��3��8��
 * @version V1.0
 */
public class BeanFactory {

    //id��IOrderDao
    public static Object getBean(String id) {
        try {
            // 1.��ȡdocument����
            Document doc = new SAXReader()
                    .read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));

            // 2.����api selectSingleNode(���ʽ)  �����id���Բ���ֵΪIOrderDao��beanԪ�ؽڵ�  ��<bean id="IOrderDao" class="cn.itcast.store.order.dao.impl.OrderDaoImpl"/>
            Element beanEle = (Element) doc.selectSingleNode("//bean[@id='" + id + "']");

            // 3.��ȡԪ�ص�class����
            String classValue = beanEle.attributeValue("class");

            // 4.ͨ�����䷵��ʵ����Ķ���
            Object newInstance = Class.forName(classValue).newInstance();
            return newInstance;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("��ȡbeanʧ��");
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getBean("IProductDao"));
    }
}
