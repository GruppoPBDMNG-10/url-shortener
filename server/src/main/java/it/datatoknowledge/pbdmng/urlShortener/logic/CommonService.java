/**
 * 
 */
package it.datatoknowledge.pbdmng.urlShortener.logic;

import spark.Request;

/**
 * @author Gianluca
 *
 */
public interface CommonService {

	/**
	 * Process a client request.
	 * @param clientRequest - client http request
	 */
	public String process(Request clientRequest);
	
	/**
	 * Exposes the API services.
	 */
	public void exposeServices();
	
}
