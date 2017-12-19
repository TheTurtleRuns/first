package cn.itcast.store.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {

	
	//���ӳ�
	private static JedisPool pool;
	//��ServletContextListener �� ���ٷ����У���������pool   pool.close()
	
	
	static{
		//��ʼ��
		//Jedis���ӳ�����
		JedisPoolConfig config = new JedisPoolConfig();
		
		//���������ǷǱ���ģ������þͻ���Ĭ�ϵ�
		//����ʱ�����������
		config.setMaxIdle(50);
		//����ʱ����С������
		config.setMinIdle(10);
		//���ӳص�������� 5000
		config.setMaxTotal(5000);
		pool=new JedisPool(config,"192.168.217.128",6379);
		
	}
	
	/**
	 * ��ȡJedis���Ӷ���
	 * 
	 */
	public static Jedis getJedis(){
		return pool.getResource();
	}
	
	/**
	 * ��ȡ���ӳ�
	 */
	public static JedisPool getJedisPool(){
		return pool;
	}
	
	/**
	 * �黹����
	 */
	public static void close(Jedis jedis){
		if(jedis!=null)
			jedis.close();
	}
	
}
