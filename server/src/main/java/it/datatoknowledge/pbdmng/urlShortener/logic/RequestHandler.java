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

import spark.Request;

/**
 * @author Gianluca
 *
 */
public class RequestHandler extends Base implements CommonService{

	/**
	 * 
	 */
	public RequestHandler() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public String process(Request clientRequest) {
		// TODO Auto-generated method stub
		debug(loggingId, "/*** Start RequestHandler.proces ***/");
		long start = System.currentTimeMillis();
		String result = null;
		String[] wildcards = clientRequest.splat();
		if (wildcards != null && wildcards.length == 1) {
			String url = clientRequest.splat()[0];
			if (url != null) {
				String userAgent = clientRequest.userAgent();
				String ip = clientRequest.ip();
				Date timestamp = new Date();
				DAOInterface dao = DAOFactory.getIstance(DAOImplementation.JEDIS);
				DAOResponse response = dao.getOrigin(url, ip, userAgent, timestamp);
				switch (response.getResultCode()) {
				case UPDATED:
					result = (String) response.getResponse();
					info(loggingId,"Url requested: " + url + " redirected to: " + result);
					break;
				case NOT_MAPPED:
					info(loggingId,"Url requested: " + url + " not present into database!");
					break;
				default: break;
				}
			}
		}
		long finish = System.currentTimeMillis();
		info(loggingId + " - Elapsed time: " + (finish - start));
		debug(loggingId, "/*** Finish RequestHandler.proces ***/");
		info(loggingId, "Response:", result);
		return result;
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
