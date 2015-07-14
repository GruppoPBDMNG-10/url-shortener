package it.datatoknowledge.pbdmng.urlShortener.bean.url;

import java.util.List;

public class BaseStatistics {

	private List<Click> clicks;
	
	private long numClicks;
	
	public BaseStatistics() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the clicks
	 */
	public List<Click> getClicks() {
		return clicks;
	}

	/**
	 * @param clicks the clicks to set
	 */
	public void setClicks(List<Click> clicks) {
		this.clicks = clicks;
	}

	/**
	 * @return the numClicks
	 */
	public long getNumClicks() {
		return numClicks;
	}

	/**
	 * @param numClicks the numClicks to set
	 */
	public void setNumClicks(long numClicks) {
		this.numClicks = numClicks;
	}
	
	

}
