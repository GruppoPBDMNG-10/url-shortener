package it.datatoknowledge.pbdmng.urlShortener.geoLocation;

import it.datatoknowledge.pbdmng.urlShortener.json.JsonManager;
import it.datatoknowledge.pbdmng.urlShortener.logic.Base;
import it.datatoknowledge.pbdmng.urlShortener.utils.Parameters;
import it.datatoknowledge.pbdmng.urlShortener.geoLocation.bean.GeoCountry;
import it.datatoknowledge.pbdmng.urlShortener.geoLocation.bean.GeoLocation;
import it.datatoknowledge.pbdmng.urlShortener.geoLocation.bean.GeoSubdivision;
import it.datatoknowledge.pbdmng.urlShortener.geoLocation.bean.GeographicInfo;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import com.maxmind.geoip2.record.Postal;
import com.maxmind.geoip2.record.Subdivision;

/**
 * Provides information about click geoLocation. 
 * @author gaetano
 *
 *
 */
public class GeoLocationService extends Base{
	
	private static DatabaseReader reader;
	private String jsonInfo;
	
	/**
	 * Default constructor.
	 * @param ipAddress the ipAddress to find.
	 */
	public GeoLocationService(String ipAddress) {
		// TODO Auto-generated constructor stub
		super();
		if (reader == null) {
			String path = serviceParameters.getValue(Parameters.GEO_LOCATION_FILE_PATH, Parameters.DEFAULT_GEO_LOCATION_FILE_PATH);
			try {
				reader = new DatabaseReader.Builder(new File(path)).build();
				jsonInfo = getLocation(ipAddress);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				error(e, loggingId, "Error during database file loading");
			}
		}
	}
	
	/**
	 * Provide the location of one ip address.
	 * @param ipAddressString the ipAddress to get location.
	 * @return a JSON format {@link String} of the location info.
	 */
	private String getLocation(String ipAddressString) {
		String result = null;
		try {
			
			InetAddress ipAddress = InetAddress.getByName(ipAddressString);
			CityResponse response = reader.city(ipAddress);
			GeographicInfo geoInfo = new GeographicInfo();
			GeoCountry geoCountry  = new GeoCountry();
			geoInfo.setCountry(geoCountry);
			GeoLocation geoLocation = new GeoLocation();
			geoInfo.setLocation(geoLocation);
			GeoSubdivision geoSubdivision = new GeoSubdivision();
			geoInfo.setSubdivision(geoSubdivision);
			
			Country country = response.getCountry();
			geoCountry.setIsoCode(country.getIsoCode());
			geoCountry.setName(country.getName());            

			Subdivision subdivision = response.getMostSpecificSubdivision();
			geoSubdivision.setName(subdivision.getName());
			geoSubdivision.setIsoCode(subdivision.getIsoCode()); 

			City city = response.getCity();
			geoInfo.setCityName(city.getName());
			
			Postal postal = response.getPostal();
			geoInfo.setPostalCode(postal.getCode());

			Location location = response.getLocation();
			geoLocation.setLatitude(location.getLatitude());  
			geoLocation.setLongitude(location.getLongitude());
			result = JsonManager.buildJson(geoInfo);
			info(loggingId, "Geo info:", result);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			error(e, loggingId, "Invalid ip address");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			error(e, loggingId, "Error during db access");
		} catch (GeoIp2Exception e) {
			// TODO Auto-generated catch block
			error(e, loggingId, "Error during db access");
		}
		return result;
	}

	/**
	 * @return the jsonInfo
	 */
	public String getJsonInfo() {
		return jsonInfo;
	}
	
}
