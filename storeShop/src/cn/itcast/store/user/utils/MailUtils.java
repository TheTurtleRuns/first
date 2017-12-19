package cn.itcast.store.user.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {

    public static void main(String[] args) {
        // sendMail("xxxx@163.com","11");
        sendMail("psj@store.com", "123456");
    }

    /**
     * �ʼ�����
     * 
     * @param to  ����
     * @param code ������
     */
    public static void sendMail(String to, String code) {
        // Session����:
        Properties props = new Properties();
        // �ʼ��������ڱ���
        props.setProperty("mail.host", "localhost");

        // �ʼ���������������Ҫ����������������� ע�⣬163��QQ��������Ҫȥ��Ӧ����ע����Ȩ��ſ��Է���
        // props.setProperty("mail.host", "smtp.163.com");
        // props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                // �ʼ��������ڱ���
                return new PasswordAuthentication("service@store.com", "123456");

                // �ʼ�������������
                // return new PasswordAuthentication("******@163.com", "1qaz2wsx");
            }

        });
        // Message����:
        Message message = new MimeMessage(session);
        // ���÷����ˣ�
        try {
            message.setFrom(new InternetAddress("service@store.com"));
            // �����ռ���:
            message.addRecipient(RecipientType.TO, new InternetAddress(to));
            // ��������:
            message.setSubject("�����������̳�ϵͳ�ļ����ʼ�");
            // �������ݣ�
            String url = "http://localhost:8080/day48/UserServlet?method=active&code=" + code;
            message.setContent(
                    "<h1>�����������̳�ϵͳ�ļ����ʼ������������������ӣ�</h1><h3><a href='" + url + "'>" + url + "</a></h3>",
                    "text/html;charset=UTF-8");
            // Transport����:
            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
 