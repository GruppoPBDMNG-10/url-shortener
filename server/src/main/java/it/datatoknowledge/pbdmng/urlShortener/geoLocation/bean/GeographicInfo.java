package it.datatoknowledge.pbdmng.urlShortener.geoLocation.bean;

/**
 * 
 * @author Gianluca Colaianni
 * Bean class to contains geographic info.
 */
public class GeographicInfo {

	private GeoLocation location;
	
	private String cityName;
	
	private String postalCode;
	
	private GeoCountry country;
	
	private GeoSubdivision subdivision;
	
	/**
	 * Default constructor.
	 */
	public GeographicInfo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get a {@link GeoLocation} object with info about longitude and latitude.
	 * @return a {@link GeoLocation} object.
	 */
	public GeoLocation getLocation() {
		return location;
	}

	/**
	 * Set a {@link GeoLocation} object with info about longitude and latitude.
	 * @param a {@link GeoLocation} object to set.
	 */
	public void setLocation(GeoLocation location) {
		this.location = location;
	}

	/**
	 * Get the city's name.
	 * @return the cityName.
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * Set the city's name.
	 * @param cityName the cityName to set.
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * Get the postal code.
	 * @return the postalCode.
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * Set the postal code.
	 * @param postalCode the postalCode to set.
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * Get a {@link GeoCountry} object with info about the country.
	 * @return the country.
	 */
	public GeoCountry getCountry() {
		return country;
	}

	/**
	 * Set a {@link GeoCountry} object with info about the country.
	 * @param country the country to set
	 */
	public void setCountry(GeoCountry country) {
		this.country = country;
	}

	/**
	 * Get a {@link GeoSubdivision} object with geographic info about states of country.
	 * @return the subdivision.
	 */
	public GeoSubdivision getSubdivision() {
		return subdivision;
	}

	/**
	 * Set a {@link GeoSubdivision} object with geographic info about states of country.
	 * @param subdivision the subdivision to set
	 */
	public void setSubdivision(GeoSubdivision subdivision) {
		this.subdivision = subdivision;
	}

}
