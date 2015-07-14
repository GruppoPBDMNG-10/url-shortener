package it.datatoknowledge.pbdmng.urlShortener.bean.url;

import java.util.Date;


public class Click {
	

	private String ip;
	
	private String userAgent;
	
	private Date timestamp;
	
	/**
	 * This is a Json element.
	 */
	private String locationInfo;

	/**
	 * 
	 */
	public Click() {
		// TODO Auto-generated constructor stub
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the userAgent
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * @param userAgent the userAgent to set
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * @return the locationInfo
	 */
	public String getLocationInfo() {
		return locationInfo;
	}

	/**
	 * @param locationInfo the locationInfo to set
	 */
	public void setLocationInfo(String locationInfo) {
		this.locationInfo = locationInfo;
	}
	
	
}
