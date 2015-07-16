package it.datatoknowledge.pbdmng.urlShortener.utils;

import it.datatoknowledge.pbdmng.urlShortener.logic.Base;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utility extends Base{

	public Utility() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public String dateToString(Date date, String format) {
		String result = null;
		try {
			if (date != null) {
				result =  DateTimeFormatter.ofPattern(format).withZone(ZoneId.systemDefault()).format(date.toInstant());	
			}
		} catch(Exception e) {
			error(e, loggingId, "Error during Date parsing");
		}
		return result;
	}
	
	public Date stringToDate(String date, String format) {
		Date result = null;
		try {
			if (date != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.parse(date);
//				DateTimeFormatter dtf = new DateTimeFormatterBuilder().appendPattern(format).appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();
//				ZonedDateTime ldt = LocalDateTime.parse(date, dtf).atZone(ZoneId.systemDefault());
//				ldt.format(DateTimeFormatter.ofPattern(Constants.DATE_PATTERN_ISO));
//				result =  Date.from(ldt.toInstant());	
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			error(e, loggingId, "Error during Date building");
		}
		return result;
	}
	
	
	
}
