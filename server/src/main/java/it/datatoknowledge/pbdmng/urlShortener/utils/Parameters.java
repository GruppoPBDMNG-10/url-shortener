package it.datatoknowledge.pbdmng.urlShortener.utils;

/**
 * 
 * @author gaetano
 *
 */
public class Parameters {

	/**
	 * List of classes that implements path root. The default value is "available_services".
	 */
	public final static String AVAILABLE_SERVICES = "available_services";
	
	/**
	 * Key to find default default value.
	 */
	public final static String TO_VALUE_STATISTICS = "to_value_statistics";
	
	/**
	 * Default up to number of clicks for statistics.
	 */
	public final static String DEFAULT_TO_VALUE_STATISTICS = "10";
	
	/**
	 * Key to find default value of ROUTE_ALL.
	 */
	public final static String ROUTE_ALL = "route_all";
	
	/**
	 * Default route for all option request.
	 */
	public final static String DEFAULT_ROUTE_ALL = "/*";
	
	/**
	 * Route for tiny.
	 */
	public final static String ROUTE_TINY = "route_tiny";
	
	/**
	 * Default root for tiny.
	 */
	public final static String DEFAULT_ROUTE_TINY = "/tiny";
	
	/**
	 * Route for statistics.
	 */
	public final static String ROUTE_STATISTICS = "route_statistics";
	
	/**
	 * Default route for statistics.
	 */
	public final static String DEFAULT_ROUTE_STATISTICS = "/statistics";
	
	/**
	 * Path of locations database.
	 */
	public final static String GEO_LOCATION_FILE_PATH = "geo_location_file_path";
	
	/**
	 * Default path of locations database.
	 */
	public final static String DEFAULT_GEO_LOCATION_FILE_PATH = "/UserTemp/location/GeoLite2-City.mmdb";
	
	/**
	 * String for testing mode.
	 */
	public final static String IS_TESTING = "testing";
	
	/**
	 * Value for testing mode.
	 */
	public final static boolean DEFAULT_IS_TESTING = true;
	
	/**
	 * String for images path.
	 */
	public final static String IMAGES_PATH = "images_path";
	
	/**
	 * Default value for images path.
	 */
	public final static String DEFAULT_IMAGES_PATH = "/images/";
	
	/**
	 * String for images route.
	 */
	public final static String ROUTE_IMAGES = "route_images";
	
	/**
	 * Default value for images route.
	 */
	public final static String DEFAULT_ROUTE_IMAGES  = DEFAULT_IMAGES_PATH + ":image";
	
	/**
	 * String for languages folder.
	 */
	public final static String LANGUAGES_FOLDER = "languages_folder";
	
	/**
	 * Default value for languages folder.
	 */
	public final static String DEFAULT_LANGUAGES_FOLDER = "/userTemp/languages";
	
	/**
	 * String for bad_words path.
	 */
	public final static String BAD_WORDS_PATH = "bad_words_path";
	
	/**
	 * Default value for badWords file path.
	 */
	public final static String DEFAULT_BAD_WORDS_PATH = "/userTemp/bad/badWords.csv";

	/**
	 * Database host url.
	 */
	public final static String DB_HOST_URL = "db_host_url";

	/**
	 * Default database host url.
	 */
	public final static String DEFAULT_DB_HOST_URL = "";
	
}
