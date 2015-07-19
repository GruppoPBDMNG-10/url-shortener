/**
 * 
 */
package it.datatoknowledge.pbdmng.urlShortener.bean.url;

/**
 * @author Gianluca
 * Contains information about a click filter by userAgent to a short url.
 *
 */
public class IpAgentStatistics extends AgentStatistics {
	
	private String ip;

	/**
	 * 
	 */
	public IpAgentStatistics() {
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
