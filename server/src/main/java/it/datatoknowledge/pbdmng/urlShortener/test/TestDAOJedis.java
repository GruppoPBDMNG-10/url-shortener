/**
 * 
 */
package it.datatoknowledge.pbdmng.urlShortener.test;

import static org.junit.Assert.*;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOFactory;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOImplementation;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOInterface;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOResponse;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOResponseCode;
import it.datatoknowledge.pbdmng.urlShortener.dao.jedis.DAOJedis;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Gianluca
 *
 */
public class TestDAOJedis {
	private String[] originalUrls;
	private String[] keys;
	private Date[] urlsTimestamp;
	private String[] ips;
	private String[] userAgents;
	private long[] clicksId;
	private Date[] clicksTimestamp;
	private final static int NUM_URL = 2;
	private final static int NUM_CLICK = 10;
	private DAOResponse[] urlsResponse;
	private DAOInterface dao;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dao = DAOFactory.getIstance(DAOImplementation.JEDIS);
		originalUrls = new String[NUM_URL];
		keys = new String[NUM_URL];
		urlsTimestamp = new Date[NUM_URL];
		ips = new String[NUM_CLICK];
		userAgents = new String[NUM_CLICK];
		clicksId = new long[NUM_CLICK];
		clicksTimestamp = new Date[NUM_CLICK];
		DAOResponse response1 = new DAOResponse();
		response1.setResultCode(DAOResponseCode.INSERTED);
		DAOResponse response2 = new DAOResponse();
		response2.setResultCode(DAOResponseCode.DUPLICATED_KEY);
		urlsResponse = new DAOResponse[NUM_URL];
		urlsResponse[0] = response1;
		urlsResponse[1] = response2;
		for (int i = 0; i < NUM_URL; i++) {
			keys[i] = "abcdef";
			originalUrls[i] = "http://www.facebook.it";
			urlsTimestamp[i] = new Date();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link it.datatoknowledge.pbdmng.urlShortener.dao.jedis.DAOJedis#newUrl(java.lang.String, java.lang.String, java.util.Date)}.
	 */
	@Test
	public final void testNewUrl() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link it.datatoknowledge.pbdmng.urlShortener.dao.jedis.DAOJedis#getOrigin(java.lang.String, java.lang.String, java.lang.String, java.util.Date)}.
	 */
	@Test
	public final void testGetOrigin() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link it.datatoknowledge.pbdmng.urlShortener.dao.jedis.DAOJedis#getStatistics(java.lang.String, int, int)}.
	 */
	@Test
	public final void testGetStatistics() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link it.datatoknowledge.pbdmng.urlShortener.dao.jedis.DAOJedis#getStatisticsIpUserAgent(java.lang.String, int, int, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testGetStatisticsIpUserAgent() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link it.datatoknowledge.pbdmng.urlShortener.dao.jedis.DAOJedis#getStatisticsIp(java.lang.String, int, int, java.lang.String)}.
	 */
	@Test
	public final void testGetStatisticsIp() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link it.datatoknowledge.pbdmng.urlShortener.dao.jedis.DAOJedis#getStatisticsUserAgent(java.lang.String, int, int, java.lang.String)}.
	 */
	@Test
	public final void testGetStatisticsUserAgent() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link it.datatoknowledge.pbdmng.urlShortener.dao.jedis.DAOJedis#getStatisticsDate(java.lang.String, int, int, java.util.Date, java.util.Date)}.
	 */
	@Test
	public final void testGetStatisticsDate() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link it.datatoknowledge.pbdmng.urlShortener.dao.jedis.DAOJedis#getStatisticsDateIpUserAgent(java.lang.String, int, int, java.util.Date, java.util.Date, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testGetStatisticsDateIpUserAgent() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link it.datatoknowledge.pbdmng.urlShortener.dao.jedis.DAOJedis#getStatisticsDateIp(java.lang.String, int, int, java.util.Date, java.util.Date, java.lang.String)}.
	 */
	@Test
	public final void testGetStatisticsDateIp() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link it.datatoknowledge.pbdmng.urlShortener.dao.jedis.DAOJedis#getStatisticsDateUserAgent(java.lang.String, int, int, java.util.Date, java.util.Date, java.lang.String)}.
	 */
	@Test
	public final void testGetStatisticsDateUserAgent() {
		fail("Not yet implemented"); // TODO
	}

}
