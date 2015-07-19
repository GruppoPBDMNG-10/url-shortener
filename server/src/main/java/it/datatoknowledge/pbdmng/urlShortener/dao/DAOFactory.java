package it.datatoknowledge.pbdmng.urlShortener.dao;

import it.datatoknowledge.pbdmng.urlShortener.dao.jedis.DAOJedis;
import it.datatoknowledge.pbdmng.urlShortener.logic.Base;

/**
 * 
 * @author Gianluca Colaianni
 * It is responsible to get the single DAO's implementation.
 *
 */
public class DAOFactory extends Base{
	
	/**
	 * Get a specific {@link DAOInterface} implementation.
	 * @param impl the desired {@link DAOImplementation}.
	 * @return a valid {@link DAOInterface} istance.
	 */
	public static DAOInterface getIstance(DAOImplementation impl) {
		DAOInterface result = null;
		
		switch (impl) {
		case JEDIS:
			result = new DAOJedis();
			break;

		default:
			break;
		}
		return result;
	}
	
	
}
