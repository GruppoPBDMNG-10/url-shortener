package it.datatoknowledge.pbdmng.urlShortener.bean.url;

/**
 * 
 * @author gaetano
 * Contains information about a click filter by il to a short url.
 *
 */
public class IpStatistics extends BaseStatistics{

	private String ip;
	
	public IpStatistics() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

}
