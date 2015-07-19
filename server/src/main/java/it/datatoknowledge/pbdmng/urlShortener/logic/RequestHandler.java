/**
 * 
 */
package it.datatoknowledge.pbdmng.urlShortener.logic;

import static spark.Spark.get;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOFactory;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOImplementation;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOInterface;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOResponse;
import it.datatoknowledge.pbdmng.urlShortener.utils.Parameters;

import java.util.Date;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import spark.Request;

/**
 * Handler of http request.
 * @author Gianluca
 *
 */
public class RequestHandler extends Base implements CommonService{

	public RequestHandler() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public String process(Request clientRequest) {
		// TODO Auto-generated method stub
		debug(loggingId, "/*** Start RequestHandler.process ***/");
		long start = System.currentTimeMillis();
		String result = null;
		String[] wildcards = clientRequest.splat();
		if (wildcards != null && wildcards.length == 1) {
			String url = clientRequest.splat()[0];
			if (url != null) {
				String browser = getBrowserFromAgent(clientRequest.userAgent());
				String ip = clientRequest.ip();
				Date timestamp = new Date();
				DAOInterface dao = DAOFactory.getIstance(DAOImplementation.JEDIS);
				DAOResponse response = dao.getOrigin(url, ip, browser, timestamp);
				switch (response.getResultCode()) {
				case UPDATED:
					result = (String) response.getResponse();
					info(loggingId,"Url requested:", url, "redirected to:" + result);
					break;
				case NOT_MAPPED:
					info(loggingId,"Url requested:", url, "not present into database!");
					break;
				default: break;
				}
			}
		}
		long finish = System.currentTimeMillis();
		info(loggingId + " - Elapsed time: " + (finish - start));
		debug(loggingId, "/*** Finish RequestHandler.process ***/");
		info(loggingId, "Response:", result);
		return result;
	}
	
	/**
	 * Get the browser name from the user agent.
	 * @param userAgent the user agent.
	 * @return the browser name.
	 */
	private String getBrowserFromAgent(String userAgent) {
		UserAgent agent = UserAgent.parseUserAgentString(userAgent);
		Browser br = agent.getBrowser();
		return br.getGroup().getName();
	}

	@Override
	public void exposeServices() {
		// TODO Auto-generated method stub
		info(loggingId,"Exposed requestHandler");
		String route = serviceParameters.getValue(Parameters.ROUTE_ALL, Parameters.DEFAULT_ROUTE_ALL);
		get(route, (req, res) -> {
			
			String destination = process(req);
			if (destination != null) {
				res.redirect(destination);
			} 
			return null;
		});
	}

}
