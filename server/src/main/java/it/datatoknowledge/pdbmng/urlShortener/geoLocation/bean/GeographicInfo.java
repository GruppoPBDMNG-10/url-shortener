package it.datatoknowledge.pdbmng.urlShortener.geoLocation.bean;

public class GeographicInfo {

	private GeoLocation location;
	
	private String cityName;
	
	private String postalCode;
	
	private GeoCountry country;
	
	private GeoSubdivision subdivision;
	
	public GeographicInfo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the location
	 */
	public GeoLocation getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(GeoLocation location) {
		this.location = location;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the country
	 */
	public GeoCountry getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(GeoCountry country) {
		this.country = country;
	}

	/**
	 * @return the subdivision
	 */
	public GeoSubdivision getSubdivision() {
		return subdivision;
	}

	/**
	 * @param subdivision the subdivision to set
	 */
	public void setSubdivision(GeoSubdivision subdivision) {
		this.subdivision = subdivision;
	}

}
