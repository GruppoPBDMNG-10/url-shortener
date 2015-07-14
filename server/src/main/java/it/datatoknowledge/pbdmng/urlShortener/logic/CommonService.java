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

	public String process(Request clientRequest);
	public void exposeServices();
	
}
