package it.datatoknowledge.pbdmng.urlShortener.bean.url;

import java.util.Date;

/**
 * 
 * @author Gianluca Colaianni.
 * Contains information about a click to a short url.
 *
 */
public class Click {
	

	private String ip;
	
	private String userAgent;
	
	private Date timestamp;
	
	/**
	 * This is a Json formatted string.
	 */
	private String locationInfo;

	/**
	 * Default constructor.
	 */
	public Click() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the ip from which the click comes.
	 * @return the ip.
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Set the ip from which the click comes.
	 * @param ip the ip to set.
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Get the click timestamp formatted in ISO 8601.
	 * @return the click timestamp.
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Set the click timestamp formatted in ISO 8601.
	 * @param timestamp the click timestamp.
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Get the click's user agent.
	 * @return the userAgent.
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * Set the click's user agent.
	 * @param userAgent the userAgent to set.
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * Get a JSON string containing the click geo location info.
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
