package it.datatoknowledge.pbdmng.urlShortener.dao;

import it.datatoknowledge.pbdmng.urlShortener.dao.jedis.DAOJedis;
import it.datatoknowledge.pbdmng.urlShortener.logic.Base;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class DAOFactory extends Base{

	private static DAOFactory istance;
	private static JedisPool pool;
	
	private DAOFactory() {
		super();
		pool = new JedisPool();
	}
	
	public static DAOInterface getIstance(DAOImplementation impl) {
		DAOInterface result = null;
		if (istance == null) {
			istance = new DAOFactory();
		}
		
		switch (impl) {
		case JEDIS:
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
			} catch (Exception e) {
				// TODO: handle exception
				istance.error(e, istance.loggingId, "FATAL ERROR: EXCEPTION DURING JEDIS POOL INSTANTIATION!!");
			}
			result = new DAOJedis(jedis);
			break;

		default:
			break;
		}
		return result;
	}
	
	
}
