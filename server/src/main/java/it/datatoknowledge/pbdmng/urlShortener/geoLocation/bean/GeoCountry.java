package it.datatoknowledge.pbdmng.urlShortener.geoLocation.bean;

/**
 * 
 * @author Gianluca Colaianni
 * Bean class for country info.
 */
public class GeoCountry {
	
	private String name;
	
	private String isoCode;

	public GeoCountry() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the country's name.
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the country's name.
	 * @param name the name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the ISO code associated with a country.
	 * @return the isoCode associated.
	 */
	public String getIsoCode() {
		return isoCode;
	}

	/**
	 * Set the ISO code associated with a country.
	 * @param isoCode the isoCode to set.
	 */
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}
	
	

}
