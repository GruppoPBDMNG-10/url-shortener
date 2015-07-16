package it.datatoknowledge.pbdmng.urlShortener.bean.url;

import java.util.List;

/**
 * 
 * @author Gianluca Colaianni.
 * Contains the processing result with information associated with generic statistics.
 */
public class BaseStatistics {

	private List<Click> clicks;
	
	private long numClicks;
	
	/**
	 * Default constructor.
	 */
	public BaseStatistics() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the {@link List} of {@link Click}'s retrieved from the statistics process.
	 * @return the {@link List} of {@link Click}'s.
	 */
	public List<Click> getClicks() {
		return clicks;
	}

	/**
	 * Set the {@link List} of {@link Click}'s retrieved from the statistics process.
	 * @param the {@link List} of {@link Click}'s.
	 */
	public void setClicks(List<Click> clicks) {
		this.clicks = clicks;
	}

	/**
	 * Get the {@link Click}
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
