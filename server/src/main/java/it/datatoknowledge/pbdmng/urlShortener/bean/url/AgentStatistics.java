package it.datatoknowledge.pbdmng.urlShortener.bean.url;

/**
 * 
 * @author Gianluca Colaianni.
 * Contains the processing result with information associated with statistics about an user agent.
 */
public class AgentStatistics extends BaseStatistics{
	
	private String agent;

	/**
	 * Default constructor.
	 */
	public AgentStatistics() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the user agent.
	 * @return the agent value or reference.
	 */
	public String getAgent() {
		return agent;
	}

	/**
	 * Set the user agent.
	 * @param agent the agent to set.
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}

}
