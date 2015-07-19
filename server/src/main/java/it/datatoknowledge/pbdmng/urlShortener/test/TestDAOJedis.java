/**
 * 
 */
package it.datatoknowledge.pbdmng.urlShortener.test;

import static org.junit.Assert.assertTrue;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOFactory;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOImplementation;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOInterface;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOResponse;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOResponseCode;

import java.util.Date;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Gianluca Colaianni.
 * To run this test, it is necessary run first an available Redis Server.
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
	private final static String KEY = "abcd123";
	private final static String[] AGENTS = {
		"Mozilla",
		"Chrome",
		"Safari",
		"Opera",
		"IE"
	};

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
			keys[i] = KEY;
			originalUrls[i] = "http://www.facebook.it";
			urlsTimestamp[i] = new Date();
		}
		Random r = new Random();
		for (int i = 0; i < NUM_CLICK; i++) {
			StringBuffer ipBuffer = new StringBuffer(r.nextInt(256));
			ipBuffer.append(".");
			ipBuffer.append(r.nextInt(256));
			ipBuffer.append(".");
			ipBuffer.append(r.nextInt(256));
			ipBuffer.append(".");
			ipBuffer.append(r.nextInt(256));
			clicksId[i] = r.nextLong();
			ips[i] = ipBuffer.toString();
			userAgents[i] = AGENTS[r.nextInt(AGENTS.length)];
			clicksTimestamp[i] = new Date();
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
		for (int i = 0; i < NUM_URL; i++) {
			
			assertTrue("Test url n. " + (i+1), urlsResponse[i].getResultCode().equals(dao.newUrl(keys[i], originalUrls[i], urlsTimestamp[i]).getResultCode()));
		}
		
	}

	/**
	 * Test method for {@link it.datatoknowledge.pbdmng.urlShortener.dao.jedis.DAOJedis#getOrigin(java.lang.String, java.lang.String, java.lang.String, java.util.Date)}.
	 */
	@Test
	public final void testGetOrigin() {
		for (int i = 0; i < NUM_CLICK; i++) {
			assertTrue("Test click n. " + (i+1), DAOResponseCode.UPDATED.equals(dao.getOrigin(KEY, ips[i], userAgents[i], clicksTimestamp[i]).getResultCode()));
		}
	}
}
