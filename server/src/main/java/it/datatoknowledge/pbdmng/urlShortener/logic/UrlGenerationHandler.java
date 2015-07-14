/**
 * 
 */
package it.datatoknowledge.pbdmng.urlShortener.logic;

import static spark.Spark.after;
import static spark.Spark.post;
import it.datatoknowledge.pbdmng.urlShortener.bean.Result;
import it.datatoknowledge.pbdmng.urlShortener.bean.url.UrlRequest;
import it.datatoknowledge.pbdmng.urlShortener.bean.url.UrlResponse;
import it.datatoknowledge.pbdmng.urlShortener.json.JsonManager;
import it.datatoknowledge.pbdmng.urlShortener.utils.Constants;
import it.datatoknowledge.pbdmng.urlShortener.utils.Parameters;
import it.datatoknowledge.pdbmng.urlShortener.dao.DAOResponse;
import it.datatoknowledge.pdbmng.urlShortener.dao.DAOResponseCode;
import it.datatoknowledge.pdbmng.urlShortener.dao.jedis.DAOJedis;

import java.util.Date;

import org.apache.commons.validator.routines.UrlValidator;

import spark.Request;

/**
 * @author Gianluca
 *
 */
public class UrlGenerationHandler extends CommonLogic implements CommonService {

	/**
	 * 
	 */
	public UrlGenerationHandler() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * Check if the given url is valid or not.
	 * 
	 * @param url
	 *            the url to check.
	 * @return true only if the url is valid, false otherwise.
	 */
	private boolean checkUrl(String url) {
		long options = UrlValidator.ALLOW_ALL_SCHEMES
				+ UrlValidator.ALLOW_2_SLASHES;
		UrlValidator validator = new UrlValidator(options);
		return validator.isValid(url);
	}

	@Override
	public String process(Request clientRequest) {
		// TODO Auto-generated method stub
		debug(loggingId, "/*** Start UrlGeneration.proces ***/");
		long start = System.currentTimeMillis();
		UrlRequest request = (UrlRequest) JsonManager.parseJson(clientRequest.body(),
				UrlRequest.class);

		UrlResponse urlResponse = new UrlResponse();
		Result result = new Result();

		result.setReturnCode(Result.GENERIC_ERROR_RETURN_CODE);
		result.setDescription(Result.GENERIC_ERROR_DESCRIPTION);
		String response = null;
		if (request != null) {
			String originalUrl = request.getUrl();
			if (checkUrl(originalUrl)) {
				int maxAttemps = Integer.parseInt(serviceParameters.getValue(
						"max_attemps", "5"));
				DAOJedis dao = new DAOJedis();
				DAOResponse responseDAO = new DAOResponse();
				DAOResponseCode resultCode = DAOResponseCode.NOT_INSERTED;
				responseDAO.setResultCode(resultCode);
				String tiny = null;
				String custom = request.getCustom();
				int cont = 0;
				do {
					tiny = getTiny(originalUrl, custom);
					custom = null; //the second time that it is executed, it will provide a random tiny url
					if (tiny != null) {
						responseDAO = dao.newUrl(tiny, originalUrl, new Date());
					}
					cont++;

				} while (responseDAO.getResultCode().equals(
						DAOResponseCode.DUPLICATED_KEY)
						&& cont < maxAttemps);

				if (responseDAO.getResultCode()
						.equals(DAOResponseCode.INSERTED)) {
					result.setDescription(Result.OK_DESCRIPTION);
					result.setReturnCode(Result.OK_RETURN_CODE);
					urlResponse.setResult(result);
					StringBuffer buffer = new StringBuffer(Constants.DOMAIN);
					buffer.append(tiny);
					urlResponse.setUrlTiny(buffer.toString());
					urlResponse.setUrl(originalUrl);
					
				}
				response = JsonManager.buildJson(urlResponse);
			} else {
				error(loggingId, "Invalid url");
			}
		} else {
			error(loggingId, " UrlRequest null");
		}
		long finish = System.currentTimeMillis();
		info(loggingId, " - Elapsed time: " + (finish - start));
		debug(loggingId, "/*** Finish UrlGeneration.proces ***/");
		info(loggingId, "Response:", result);
		return response;
	}

	@Override
	public void exposeServices() {
		// TODO Auto-generated method stub
		String route = serviceParameters.getValue(Parameters.ROUTE_TINY, Parameters.DEFAULT_ROUTE_TINY);
		
		post(route, "application/json", (req, res) -> {
			return process(req);
		});

		after((req, res) -> {
			res.type("application/json");
		});
	}

}
