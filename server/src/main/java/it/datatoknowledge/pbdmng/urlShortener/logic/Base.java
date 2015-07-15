package it.datatoknowledge.pbdmng.urlShortener.logic;

import static spark.Spark.options;
import static spark.Spark.before;

import it.datatoknowledge.pbdmng.urlShortener.parameters.ServiceParameters;
import it.datatoknowledge.pbdmng.urlShortener.utils.Constants;
import it.datatoknowledge.pbdmng.urlShortener.utils.Parameters;

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

					return null;
				});

		

		// get("/",
		// (request, response) -> {
		//
		// response.redirect("http://localhost/index.html");
		// return null;
		// });
	}
}
