package it.datatoknowledge.pbdmng.urlShortener.json;

import it.datatoknowledge.pbdmng.urlShortener.logic.Base;
import it.datatoknowledge.pbdmng.urlShortener.utils.Constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * 
 * @author Gianluca Colaianni
 * JSON Manager. This is a Singletone class.
 *
 */
public class JsonManager extends Base{
	
	private static JsonManager manager;
	private static Gson gson;
	
	private JsonManager() {
		super();
		gson = new GsonBuilder().setDateFormat(Constants.DATE_PATTERN_ISO).create();
	}
	
	/**
	 * Parse an object into a JSON {@link String} format.
	 * @param obj the object to convert.
	 * @return a JSON {@link String} if the parse is done, false otherwise.
	 */
	public static String buildJson(Object obj) {
		long start = System.currentTimeMillis();
		if (manager == null) {
			manager  = new JsonManager();
		}
		String result = gson.toJson(obj);
		long finish = System.currentTimeMillis();
		System.out.println("Elapsed time: " + (finish - start));
		return result;
	}
	
	/**
	 * 
	 * @param jsonString
	 * @param c
	 * @return
	 */
	public static Object parseJson(String jsonString, Class<?> c) {
		Object result = null;
		long start = System.currentTimeMillis();
		if (manager == null) {
			manager  = new JsonManager();
		}
		try {
			result = gson.fromJson(jsonString, c);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		
		long finish = System.currentTimeMillis();
		System.out.println("Elapsed time: " + (finish - start));
		return result;
	}

}
