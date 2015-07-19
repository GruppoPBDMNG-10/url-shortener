package it.datatoknowledge.pbdmng.urlShortener.utils;

import it.datatoknowledge.pbdmng.urlShortener.logic.Base;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;

/**
 * Utility class.
 * @author gaetano
 *
 */
public class Utility extends Base{

	public Utility() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	/**
	 * Convert one date to string.
	 */
	public String dateToString(Date date) {
		String result = null;
		try {
			if (date != null) {
				result =  DateTimeFormatter.ofPattern(Constants.DATE_PATTERN_TIMESTAMP_CENTS).withZone(ZoneId.systemDefault()).format(date.toInstant());	
			}
		} catch(Exception e) {
			error(e, loggingId, "Error during Date parsing");
		}
		return result;
	}
	
	/**
	 * Convert one string to Date.
	 */
	public Date stringToDate(String date) {
		Date result = null;
		try {
			if (date != null) {
				DateTimeFormatter dtf = new DateTimeFormatterBuilder().appendPattern(Constants.DATE_PATTERN_TIMESTAMP).appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();
				ZonedDateTime ldt = LocalDateTime.parse(date, dtf).atZone(ZoneId.systemDefault());
				ldt.format(DateTimeFormatter.ofPattern(Constants.DATE_PATTERN_TIMESTAMP_CENTS));
				result =  Date.from(ldt.toInstant());	
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			error(e, loggingId, "Error during Date building");
		}
		return result;
	}
	
}
