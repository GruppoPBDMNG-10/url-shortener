package it.datatoknowledge.pbdmng.urlShortener.logic;

import static spark.Spark.before;
import static spark.Spark.options;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOFactory;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOImplementation;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOInterface;
import it.datatoknowledge.pbdmng.urlShortener.parameters.ServiceParameters;
import it.datatoknowledge.pbdmng.urlShortener.utils.Constants;
import it.datatoknowledge.pbdmng.urlShortener.utils.Parameters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import net.glxn.qrgen.core.image.ImageType;

import org.apache.log4j.Logger;

import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

/**
 * Abstract class Base, collects all common attribute and method of services calsses.
 * 
 * @author gaetano
 *
 */
public abstract class Base {

	protected Logger logger;
	protected String loggingId;
	protected static ServiceParameters serviceParameters;
	private static boolean isTesting = false;
	protected static List<String> blackList;

	private final static String ACCESS_REQUEST_HEADERS = "Access-Control-Request-Headers";
	private final static String ACCESS_ALLOW_HEADERS = "Access-Control-Allow-Headers";

	private final static String ACCESS_REQUEST_METHOD = "Access-Control-Request-Method";
	private final static String ACCESS_ALLOW_METHODS = "Access-Control-Allow-Methods";

	private final static String ACCES_CONTROLL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

	private final static String OK = "OK";
	
	private final static char SEMICOLON = ';';

	protected Base() {
		logger = Logger.getLogger(this.getClass());
		loggingId = this.getClass().getSimpleName();
		serviceParameters = ServiceParameters.getIstance();
	}

	protected void info(Object... messages) {
		String message = toLog(messages);
		logger.info(message);
		if (isTesting) {
			System.out.println("[INFO] " + message);
		}
	}

	protected void error(Object... messages) {
		String message = toLog(messages);
		logger.error(message);
		if (isTesting) {
			System.out.println("[ERROR] " + message);
		}
	}

	protected void error(Throwable t, Object... messages) {
		String message = toLog(messages);
		logger.error(message, t);
		if (isTesting) {
			System.out.println("[ERROR] " + message);
		}
	}

	protected void debug(Object... messages) {
		String message = toLog(messages);
		logger.debug(message);
		if (isTesting) {
			System.out.println("[DEBUG] " + message);
		}
	}

	/**
	 * Transforms Object's array into log messages.
	 * 
	 * @param messages
	 *            the array to transform
	 * @return a {@link String} containing the message to log.
	 */
	private String toLog(Object... messages) {
		StringBuffer buffer = new StringBuffer();
		for (Object obj : messages) {
			if (obj != null) {
				buffer.append(obj.toString());
			} else {
				buffer.append("null");
			}
			buffer.append(Constants.BLANK);
		}
		if (isTesting) {
			System.out.println(buffer.toString());
		}
		return buffer.toString();
	}

	/**
	 * Load base application configurations.
	 */
	protected void setUp() {
		generateCSV();
		loadBlackList();
		isTesting = serviceParameters.getValue(Parameters.IS_TESTING, Parameters.DEFAULT_IS_TESTING);
		if (isTesting) {
			populateDB();
			info(loggingId, "Executed populateDB");
		}

		String route = serviceParameters.getValue(Parameters.ROUTE_ALL, Parameters.DEFAULT_ROUTE_ALL);

		before((request, response) -> {

			response.header(ACCES_CONTROLL_ALLOW_ORIGIN, Constants.ASTERISK);
		});

		options(route, (request, response) -> {

			String accessControlRequestHeaders = request.headers(ACCESS_REQUEST_HEADERS);
			if (accessControlRequestHeaders != null) {
				response.header(ACCESS_ALLOW_HEADERS, accessControlRequestHeaders);
			}

			String accessControlRequestMethod = request.headers(ACCESS_REQUEST_METHOD);
			if (accessControlRequestMethod != null) {
				response.header(ACCESS_ALLOW_METHODS, accessControlRequestMethod);
			}

			return OK;
		});
	}

	/**
	 * This is a method to test the application.
	 */
	private void populateDB() {
		String url = "test";
		String[] ips = { "194.132.118.121", "80.76.155.148", "192.84.36.16", "193.104.112.20", "54.69.206.247", "158.81.201.220", "192.185.5.96", "162.246.58.237", "173.230.198.21", "194.132.118.121" };

		String[] agents = { "Firefox", "IE", "Safari", "Chrome", "Opera" };

		String[] urls = { "http://www.facebook.it", "http://www.site24x7.com", "http://stackoverflow.com/" };

		String[] tiny = new String[3];
		Random r = new Random();
		DAOInterface dao = DAOFactory.getIstance(DAOImplementation.JEDIS);
		for (int i = 0; i < tiny.length; i++) {
			String longUrl = urls[i];
			tiny[i] = url + i;
			dao.deleteKeys(tiny[i]);
			dao.newUrl(tiny[i], longUrl, new Date());
		}

		for (int i = 0; i < ips.length; i++) {
			dao.getOrigin(tiny[r.nextInt(tiny.length)], ips[i], agents[r.nextInt(agents.length)], new Date());
		}

		info(loggingId, "Tiny url added:", tiny);
	}

	
	
	/**
	 * Return URL where QRCode image is available for the specified tiny.
	 * @param tiny the tiny.
	 * @return the URL to QRCode image.
	 */
	protected String getImagesUrl(String tiny) {
		StringBuffer bufferImage = new StringBuffer(Constants.DOMAIN.substring(BigInteger.ZERO.intValue(), Constants.DOMAIN.length() - 1));
		bufferImage.append(serviceParameters.getValue(Parameters.IMAGES_PATH, Parameters.DEFAULT_IMAGES_PATH));
		bufferImage.append(tiny);
		bufferImage.append(Constants.DOT);
		bufferImage.append(ImageType.PNG.toString());
		return bufferImage.toString();
	}

	private void loadBlackList() {
		CsvParserSettings settings = new CsvParserSettings();
		CsvFormat format = new CsvFormat();
		format.setDelimiter(SEMICOLON);
		settings.setFormat(format);
		CsvParser parser = new CsvParser(settings);
		try {
			String badWordsFile = serviceParameters.getValue(Parameters.BAD_WORDS_PATH, Parameters.DEFAULT_BAD_WORDS_PATH);
			List<String[]> allRows = parser.parseAll(new FileReader(badWordsFile));
			blackList = new ArrayList<String>();
			for (String[] row : allRows) {
				for (String s : row) {
					if (s != null) {
						if (!(s.equalsIgnoreCase(Constants.BLANK) || s.equalsIgnoreCase(Constants.EMPTY))) {
							blackList.add(s);
						}
					}
				}
			}
			Collections.sort(blackList);
			info(loggingId, "Loaded", blackList.size(), "bad words");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			error(loggingId, e, "BadWords.csv file not found");
		}
	}

	private void generateCSV() {
		String languagesFolder = serviceParameters.getValue(Parameters.LANGUAGES_FOLDER, Parameters.DEFAULT_LANGUAGES_FOLDER);
		ArrayList<String> languagesFile = new ArrayList<String>();
		try {
			Files.walk(Paths.get(languagesFolder)).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					info(loggingId, "Found language file at location:", filePath);
					languagesFile.add(filePath.toString());
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			error(loggingId, e, "Error during languages file loading");
		}

		InputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		String line = null;
		StringBuffer buffer = new StringBuffer();
		ArrayList<String> linesToWrite = new ArrayList<String>();
		int cont = 0;
		for (String path : languagesFile) {
			try {

				fis = new FileInputStream(path);
				isr = new InputStreamReader(fis);
				br = new BufferedReader(isr);
				while ((line = br.readLine()) != null) {

					cont++;
					buffer.append(line);
					buffer.append(String.valueOf(SEMICOLON));
					if (cont % 510 == 0) {
						linesToWrite.add(buffer.toString());
						cont = 0;
						buffer = new StringBuffer();
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
					if (isr != null) {
						isr.close();
					}
					if (br != null) {
						br.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		linesToWrite.add(buffer.toString());
		PrintStream fileStream = null;
		String badWordsFile = serviceParameters.getValue(Parameters.BAD_WORDS_PATH, Parameters.DEFAULT_BAD_WORDS_PATH);
		try {
			fileStream = new PrintStream(new File(badWordsFile));
			for (String s : linesToWrite) {
				fileStream.println(s);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			error(loggingId, e);
		} finally {
			if (fileStream != null) {
				fileStream.close();
			}
		}
	}
}
