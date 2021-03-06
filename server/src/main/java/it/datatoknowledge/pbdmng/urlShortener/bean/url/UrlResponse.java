package it.datatoknowledge.pbdmng.urlShortener.bean.url;

import it.datatoknowledge.pbdmng.urlShortener.bean.BaseResponse;

public class UrlResponse extends BaseResponse{
	
	private String urlTiny;
	
	private String url;
	
	private String QRCode;
	
	private Statistics statistics;

	public UrlResponse() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * @return the urlTiny
	 */
	public String getUrlTiny() {
		return urlTiny;
	}

	/**
	 * @param urlTiny the urlTiny to set
	 */
	public void setUrlTiny(String urlTiny) {
		this.urlTiny = urlTiny;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the statistics
	 */
	public Statistics getStatistics() {
		return statistics;
	}

	/**
	 * @param statistics the statistics to set
	 */
	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	/**
	 * Return qRCode value or reference.
	 * @return qRCode value or reference.
	 */
	public String getQRCode() {
		return QRCode;
	}

	/**
	 * Set qRCode value or reference.
	 * @param qRCode Value to set.
	 */
	public void setQRCode(String qRCode) {
		QRCode = qRCode;
	}

}
