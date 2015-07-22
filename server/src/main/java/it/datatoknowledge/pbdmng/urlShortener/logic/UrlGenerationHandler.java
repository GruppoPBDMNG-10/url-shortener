/**
 * 
 */
package it.datatoknowledge.pbdmng.urlShortener.logic;

import static spark.Spark.after;
import static spark.Spark.post;
import it.datatoknowledge.pbdmng.urlShortener.bean.Result;
import it.datatoknowledge.pbdmng.urlShortener.bean.url.UrlRequest;
import it.datatoknowledge.pbdmng.urlShortener.bean.url.UrlResponse;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOFactory;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOImplementation;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOInterface;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOResponse;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOResponseCode;
import it.datatoknowledge.pbdmng.urlShortener.json.JsonManager;
import it.datatoknowledge.pbdmng.urlShortener.utils.Constants;
import it.datatoknowledge.pbdmng.urlShortener.utils.Parameters;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.InvalidPathException;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.validator.routines.UrlValidator;

import spark.Request;

/**
 * Handler of tiny generator.
 * @author Gianluca
 *
 */
public class UrlGenerationHandler extends Base implements CommonService {
	
	private final static String[] INVALID_CUSTOM_SYMBOLS = {"?", "&", "=", Constants.BLANK, "/", "\\", Constants.DOT, "%"};

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
		debug(loggingId, "/*** Start UrlGeneration.process ***/");
		long start = System.currentTimeMillis();
		UrlRequest request = (UrlRequest) JsonManager.parseJson(clientRequest.body(),
				UrlRequest.class);
		
		UrlResponse urlResponse = new UrlResponse();
		Result result = new Result();

		result.setReturnCode(Result.GENERIC_ERROR_RETURN_CODE);
		result.setDescription(Result.GENERIC_ERROR_DESCRIPTION);
		urlResponse.setResult(result);
		String response = null;
		if (request != null) {
			String originalUrl = request.getUrl();
			if (checkUrl(originalUrl)) {
				int maxAttemps = Integer.parseInt(serviceParameters.getValue(
						"max_attemps", "5"));
				DAOInterface dao = DAOFactory.getIstance(DAOImplementation.JEDIS);
				DAOResponse responseDAO = new DAOResponse();
				DAOResponseCode resultCode = DAOResponseCode.NOT_INSERTED;
				responseDAO.setResultCode(resultCode);
				String tiny = null;
				String custom = request.getCustom();
				boolean isCustom = false;
				boolean isValidCustom = false;
				if (custom != null) {
					isCustom = true;
					if (isValidCustom(custom)) {
						isValidCustom = true;
						info(loggingId, "Desired custom:", custom);
						isCustom = true;
						responseDAO = dao.newUrl(custom, originalUrl, new Date());
						tiny = custom;
					} 
				} else {
					int cont = 0;
					TinyGenerator gen = new TinyGenerator();
					do {
						tiny = gen.getTiny(originalUrl);
						if (tiny != null) {
							responseDAO = dao.newUrl(tiny, originalUrl, new Date());
						}
						cont++;

					} while (responseDAO.getResultCode().equals(
							DAOResponseCode.DUPLICATED_KEY)
							&& cont < maxAttemps);
				}
				
				if (responseDAO.getResultCode()
						.equals(DAOResponseCode.INSERTED)) {
					result.setDescription(Result.OK_DESCRIPTION);
					result.setReturnCode(Result.OK_RETURN_CODE);
					StringBuffer buffer = new StringBuffer(Constants.DOMAIN);
					buffer.append(tiny);
					urlResponse.setUrlTiny(buffer.toString());
					urlResponse.setUrl(originalUrl);
					setQrLink(urlResponse, tiny);
				} else if (isCustom) {
					if (isValidCustom) {
						result.setDescription(Result.DUPLICATED_ERROR_DESCRIPTION);
						result.setReturnCode(Result.DUPLICATED_ERROR_RETURN_CODE);
					} else {
						result.setDescription(Result.NOT_VALID_CUSTOM_DESCRIPTION);
						result.setReturnCode(Result.NOT_VALID_CUSTOM);
					}
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
		debug(loggingId, "/*** Finish UrlGeneration.process ***/");
		info(loggingId, "Response:", response);
		return response;
	}

	@Override
	public void exposeServices() {
		// TODO Auto-generated method stub
		info(loggingId,"Exposed UrlGenerationHandler");
		String route = serviceParameters.getValue(Parameters.ROUTE_TINY, Parameters.DEFAULT_ROUTE_TINY);
		
		post(route, "application/json", (req, res) -> {
			return process(req);
		});

		after((req, res) -> {
			res.type("application/json");
		});
	}
	
	/**
	 * Check if the desired custom is valid and if it not contains bad words.
	 * @param custom the custom to check.
	 * @return true only if is a valid custom.
	 */
	private boolean isValidCustom(String custom) {
		boolean isValid = false;
		if (custom != null && !custom.equals(Constants.EMPTY)) {
			for (String s : INVALID_CUSTOM_SYMBOLS) {
				isValid =  (!custom.contains(s));
			}
			isValid = Collections.binarySearch(blackList, custom) < BigInteger.ZERO.intValue();
		}
		return isValid;
	}
	
	/**
	 * Set the reference to QRCode image link into response.
	 * 
	 * @param response
	 * @param tiny
	 */
	private void setQrLink(UrlResponse response, String tiny) {
		StringBuffer path = new StringBuffer(serviceParameters.getValue(Parameters.IMAGES_PATH, Parameters.DEFAULT_IMAGES_PATH));
		path.append(tiny);
		String qrCodePath = null;
		try {
			StringBuffer buffer = new StringBuffer(Constants.DOMAIN);
			buffer.append(tiny);
			qrCodePath = QRCodeGenerator.createQRCode(buffer.toString(), path.toString());
			response.setQRCode(getImagesUrl(tiny));
			info(loggingId, "QrCode correctly generated at path:", qrCodePath);
		} catch (InvalidPathException | NullPointerException | IOException e) {
			// TODO Auto-generated catch block
			error(loggingId, e, "Impossible generate QRCode for short url", tiny, "at filePath:", qrCodePath);
		}
	}

}
