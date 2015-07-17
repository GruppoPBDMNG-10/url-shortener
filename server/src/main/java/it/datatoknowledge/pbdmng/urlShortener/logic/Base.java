package it.datatoknowledge.pbdmng.urlShortener.logic;

import static spark.Spark.before;
import static spark.Spark.options;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOFactory;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOImplementation;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOInterface;
import it.datatoknowledge.pbdmng.urlShortener.parameters.ServiceParameters;
import it.datatoknowledge.pbdmng.urlShortener.utils.Constants;
import it.datatoknowledge.pbdmng.urlShortener.utils.Parameters;

import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

public abstract class Base {

	protected Logger logger;
	protected String loggingId;
	protected static ServiceParameters serviceParameters;

	private final static String ACCESS_REQUEST_HEADERS = "Access-Control-Request-Headers";
	private final static String ACCESS_ALLOW_HEADERS = "Access-Control-Allow-Headers";

	private final static String ACCESS_REQUEST_METHOD = "Access-Control-Request-Method";
	private final static String ACCESS_ALLOW_METHODS = "Access-Control-Allow-Methods";

	private final static String ACCES_CONTROLL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	
	private final static String OK = "OK";

	protected Base() {
		logger = Logger.getLogger(this.getClass());
		loggingId = this.getClass().getSimpleName();
		serviceParameters = ServiceParameters.getIstance();
	}

	protected void info(Object... messages) {
		logger.info(toLog(messages));
	}

	protected void error(Object... messages) {
		logger.error(toLog(messages));
	}

	protected void error(Throwable t, Object... messages) {
		logger.error(toLog(messages), t);
	}

	protected void debug(Object... messages) {
		logger.debug(toLog(messages));
	}

	private String toLog(Object... messages) {
		StringBuffer buffer = new StringBuffer();
		for (Object obj : messages) {
			if (obj != null) {
				buffer.append(obj.toString());
			} else {
				buffer.append("null");
			}
			buffer.append(Constants.BLANK);
		}
		return buffer.toString();
	}

	/**
	 * Load base application configurations.
	 */
	protected void setUp() {
		
		boolean isTesting = serviceParameters.getValue(Parameters.IS_TESTING, Parameters.DEFAULT_IS_TESTING);
		if (isTesting) {
			populateDB();
			info(loggingId, "Executed populateDB");
		}

		String route = serviceParameters.getValue(Parameters.ROUTE_ALL,
				Parameters.DEFAULT_ROUTE_ALL);
		
		before((request, response) -> {

			response.header(ACCES_CONTROLL_ALLOW_ORIGIN, Constants.ASTERISK);
		});

		options(route,
				(request, response) -> {

					String accessControlRequestHeaders = request
							.headers(ACCESS_REQUEST_HEADERS);
					if (accessControlRequestHeaders != null) {
						response.header(ACCESS_ALLOW_HEADERS,
								accessControlRequestHeaders);
					}

					String accessControlRequestMethod = request
							.headers(ACCESS_REQUEST_METHOD);
					if (accessControlRequestMethod != null) {
						response.header(ACCESS_ALLOW_METHODS,
								accessControlRequestMethod);
					}

					return OK;
				});
	}
	
	private void populateDB() {
		String url = "test";
		String[] ips = {
				"194.132.118.121",
				"80.76.155.148",
				"192.84.36.16",
				"193.104.112.20",
				"54.69.206.247",
				"158.81.201.220",
				"192.185.5.96",
				"162.246.58.237",
				"173.230.198.21",
				"194.132.118.121"
		};
		
		String[] agents = {
				"Firefox",
				"IE",
				"Safari",
				"Chrome",
				"Opera"
		};
		
		String[] urls = {
				"http://www.facebook.it",
				"http://www.site24x7.com",
				"http://stackoverflow.com/"
		};
		
		String[] tiny = new String[3];
		Random r = new Random();
		DAOInterface dao = DAOFactory.getIstance(DAOImplementation.JEDIS);
		for (int i = 0; i < tiny.length; i++) {
			String longUrl = urls[i];
			tiny[i] = url+i;
			dao.deleteKeys(tiny[i]);
			dao.newUrl(tiny[i], longUrl, new Date());
		}
		
		for (int i = 0; i < ips.length; i++) {
			dao.getOrigin(tiny[r.nextInt(tiny.length)], ips[i], agents[r.nextInt(agents.length)], new Date());
		}
		
		info(loggingId, "Tiny url added:", tiny);
	}
}
