package it.datatoknowledge.pbdmng.urlShortener.geoLocation.bean;

/**
 * 
 * @author Gianluca Colaianni
 * Contains geographic info about states of country.
 */
public class GeoSubdivision {
	
	private String isoCode;
	
	private String name;

	/**
	 * Default constructor.
	 */
	public GeoSubdivision() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the state ISO code value.
	 * @return the isoCode value.
	 */
	public String getIsoCode() {
		return isoCode;
	}

	/**
	 * Set the state ISO code value.
	 * @param isoCode the isoCode to set.
	 */
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	/**
	 * Get state's name.
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set state's name.
	 * @param name the name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

}
