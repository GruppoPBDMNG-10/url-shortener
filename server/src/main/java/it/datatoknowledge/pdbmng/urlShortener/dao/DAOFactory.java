package it.datatoknowledge.pdbmng.urlShortener.dao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class DAOFactory {

	private static DAOFactory istance;
	private static JedisPool pool;
	
	private DAOFactory() {
		pool = new JedisPool();
	}
	
	public static Jedis getIstance() {
		Jedis result = null;
		if (istance == null) {
			istance = new DAOFactory();
		}
		try (Jedis jedis = pool.getResource()) {
			result = jedis;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
