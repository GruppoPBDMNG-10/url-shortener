/**
 * 
 */
package it.datatoknowledge.pbdmng.urlShortener.bean.url;

import java.util.Date;



/**
 * @author Gianluca
 *
 */
public class Statistics extends BaseStatistics{
	
	private Click lastClick;
	
	private Date inserted;
	
	private AgentStatistics agentStat;
	
	private IpStatistics ipStat;
	
	private IpAgentStatistics ipAgentStat;

	/**
	 * 
	 */
	public Statistics() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the lastClick
	 */
	public Click getLastClick() {
		return lastClick;
	}

	/**
	 * @param lastClick the lastClick to set
	 */
	public void setLastClick(Click lastClick) {
		this.lastClick = lastClick;
	}

	/**
	 * @return the inserted
	 */
	public Date getInserted() {
		return inserted;
	}

	/**
	 * @param inserted the inserted to set
	 */
	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}

	/**
	 * @return the agentsStat
	 */
	public AgentStatistics getAgentsStat() {
		return agentStat;
	}

	/**
	 * @param agentsStat the agentsStat to set
	 */
	public void setAgentsStat(AgentStatistics agentsStat) {
		this.agentStat = agentsStat;
	}

	/**
	 * @return the ipStat
	 */
	public IpStatistics getIpStat() {
		return ipStat;
	}

	/**
	 * @param ipStat the ipStat to set
	 */
	public void setIpStat(IpStatistics ipStat) {
		this.ipStat = ipStat;
	}

	/**
	 * @return the ipAgentStat
	 */
	public IpAgentStatistics getIpAgentStat() {
		return ipAgentStat;
	}

	/**
	 * @param ipAgentStat the ipAgentStat to set
	 */
	public void setIpAgentStat(IpAgentStatistics ipAgentStat) {
		this.ipAgentStat = ipAgentStat;
	}

}
