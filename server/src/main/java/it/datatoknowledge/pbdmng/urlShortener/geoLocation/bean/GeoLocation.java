package it.datatoknowledge.pbdmng.urlShortener.geoLocation.bean;

/**
 * 
 * @author Gianluca Colaianni.
 * Contains geografic info about a location, with latitude and longitude.
 *
 */
public class GeoLocation {
	
	private double latitude;
	
	private double longitude;

	/**
	 * Default constructor.
	 */
	public GeoLocation() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the latitude.
	 * @return the latitude.
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Set the latitude.
	 * @param latitude the latitude to set.
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Get the longitude.
	 * @return the longitude.
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Set the longitude.
	 * @param longitude the longitude to set.
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	
}
