package it.datatoknowledge.pbdmng.urlShortener.bean;

/**
 * 
 * @author Gianluca Colaianni
 * This class is the base class for all request.
 *
 */
public class BaseRequest {
	
	private String url;
	
	private String custom;
	
	/**
	 * Default construtor.
	 */
	public BaseRequest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Return the url value or reference.
	 * @return url value or reference.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set the url value or reference.
	 * @param url value or reference.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Get the required custom short url required.
	 * @return custom value or reference.
	 */
	public String getCustom() {
		return custom;
	}

	/**
	 * Set the custom short url required.
	 * @param custom value or reference.
	 */
	public void setCustom(String custom) {
		this.custom = custom;
	}

}
