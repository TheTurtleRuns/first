package cn.itcast.store.utils;

import java.util.UUID;

public class UUIDUtils {

    /**
     * ���32���ȵ�UUID�ַ���
     * 
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * ���64���ȵ�UUID�ַ���
     * 
     * @return
     */
    public static String getUUID64() {
        return getUUID() + getUUID();
    }

}
