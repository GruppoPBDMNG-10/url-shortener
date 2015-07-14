/**
 * 
 */
package it.datatoknowledge.pbdmng.urlShortener.parameters;

import it.datatoknowledge.pbdmng.urlShortener.utils.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Properties;

/**
 * @author Gianluca
 *
 */
public class ServiceParameters {
	
	private static Properties properties;
	private static ServiceParameters serviceParameters;
	private static boolean isAvailable = false;

	/**
	 * 
	 */
	private ServiceParameters() {
		// TODO Auto-generated constructor stub
		properties = new Properties();
		try {
			StringBuffer filePath = new StringBuffer(System.getenv("SystemDrive"));
			filePath.append("/userTemp/conf/conf.conf");
			properties.load(new FileInputStream(filePath.toString()));
			isAvailable = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static ServiceParameters getIstance() {
		if (serviceParameters == null) {
			serviceParameters = new ServiceParameters();
		} else if (!serviceParameters.isAvialable()) {
			serviceParameters = new ServiceParameters();
		}
		return serviceParameters;
	}
	
	private boolean isAvialable() {
		return isAvailable;
	}

	public String getValue(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
	
	public LinkedHashMap<String, Object> getValue(String key, LinkedHashMap<String, Object> defaultValue) {
		String values = properties.getProperty(key);
		LinkedHashMap<String, Object> result = null;
		if (values != null) {
			String[] properties = values.split(Constants.COMMA);
			result = new LinkedHashMap<String, Object>();
			for (String s : properties) {
				String[] mapping = s.split(Constants.COLON);
				result.put(mapping[0], mapping[1]);
			}
		}
		return (result == null) ? defaultValue : result;
	}
}
