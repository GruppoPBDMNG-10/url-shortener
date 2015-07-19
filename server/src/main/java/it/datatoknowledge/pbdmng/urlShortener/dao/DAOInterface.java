package it.datatoknowledge.pbdmng.urlShortener.dao;

import java.util.Date;

/**
 * 
 * @author Gianluca Colaianni Services exposed by every DAO implementation
 *
 */
public interface DAOInterface {

	/**
	 * Insert a new URL into the DB.
	 * 
	 * @param key
	 *            the short url to insert.
	 * @param originalUrl
	 *            the original long url.
	 * @param timestamp
	 *            the timestamp.
	 * @return a {@link DAOResponse} object containing the operation's response.
	 */
	public DAOResponse newUrl(String key, String originalUrl, Date timestamp);

	/**
	 * Only test method.
	 * 
	 * @param args
	 */
	public void deleteKeys(String... args);

	/**
	 * Get the original url associated with a short one.
	 * 
	 * @param url
	 *            the short url to match.
	 * @param ip
	 *            the ip from which the request comes.
	 * @param userAgent
	 *            agent from which the request comes.
	 * @param timestamp
	 *            the timestamp.
	 * @return a {@link DAOResponse} object containing the operation's response.
	 */
	public DAOResponse getOrigin(String url, String ip, String userAgent,
			Date timestamp);

	/**
	 * Get statistics about a short url.
	 * 
	 * @param url
	 *            the short url to match.
	 * @param from
	 *            the start number of clicks to retrieve.
	 * @param to
	 *            the stop number of clicks to retrieve.
	 * @return a {@link DAOResponse} object containing the operation's response.
	 */
	public DAOResponse getStatistics(String url, int from, int to);

	/**
	 * Get statistics about a short url required from the specified ip.
	 * 
	 * @param url
	 *            the short url to match.
	 * @param from
	 *            the start number of clicks to retrieve.
	 * @param to
	 *            the stop number of clicks to retrieve.
	 * @param ip
	 *            the ip from witch clicks come.
	 * @return a {@link DAOResponse} object containing the operation's response.
	 */
	public DAOResponse getStatisticsIp(String url, int from, int to, String ip);

	/**
	 * Get statistics about a short url required from the specified ip and user
	 * agent.
	 * 
	 * @param url
	 *            the short url to match.
	 * @param from
	 *            the start number of clicks to retrieve.
	 * @param to
	 *            the stop number of clicks to retrieve.
	 * @param ip
	 *            the ip from which clicks come.
	 * @param userAgent
	 *            from which clicks come.
	 * @return a {@link DAOResponse} object containing the operation's response.
	 */
	public DAOResponse getStatisticsIpUserAgent(String url, int from, int to,
			String ip, String userAgent);

	/**
	 * Get statistics about a short url required from the specified ip and user
	 * agent.
	 * 
	 * @param url
	 *            the short url to match.
	 * @param from
	 *            the start number of clicks to retrieve.
	 * @param to
	 *            the stop number of clicks to retrieve.
	 * @param userAgent
	 *            from which clicks come.
	 * @return a {@link DAOResponse} object containing the operation's response.
	 */
	public DAOResponse getStatisticsUserAgent(String url, int from, int to,
			String userAgent);

	/**
	 * Get statistics about a short url between two dates.
	 * 
	 * @param url
	 *            the short url to match.
	 * @param from
	 *            the start number of clicks to retrieve.
	 * @param to
	 *            the stop number of clicks to retrieve.
	 * @param dateFrom
	 *            the start date.
	 * @param dateTo
	 *            the end date.
	 * @return a {@link DAOResponse} object containing the operation's response.
	 */
	public DAOResponse getStatisticsDate(String url, int from, int to,
			Date dateFrom, Date dateTo);

	/**
	 * Get statistics about a short url between two dates from the specified ip.
	 * 
	 * @param url
	 *            the short url to match.
	 * @param from
	 *            the start number of clicks to retrieve.
	 * @param to
	 *            the stop number of clicks to retrieve.
	 * @param dateFrom
	 *            the start date.
	 * @param dateTo
	 *            the end date.
	 * @param ip
	 *            the ip from which clicks come.
	 * @return a {@link DAOResponse} object containing the operation's response.
	 */
	public DAOResponse getStatisticsDateIp(String url, int from, int to,
			Date dateFrom, Date dateTo, String ip);

	/**
	 * 
	 * Get statistics about a short url between two dates from the specified ip
	 * and user agent.
	 * 
	 * @param url
	 *            the short url to match.
	 * @param from
	 *            the start number of clicks to retrieve.
	 * @param to
	 *            the stop number of clicks to retrieve.
	 * @param dateFrom
	 *            the start date.
	 * @param dateTo
	 *            the end date.
	 * @param ip
	 *            the ip from which clicks come.
	 * @param userAgent
	 *            the user agent from which clicks comes.
	 * @return a {@link DAOResponse} object containing the operation's response.
	 */
	public DAOResponse getStatisticsDateIpUserAgent(String url, int from,
			int to, Date dateFrom, Date dateTo, String ip, String userAgent);

	/**
	 * Get statistics about a short url between two dates from the specified
	 * user agent.
	 * 
	 * @param url
	 *            the short url to match.
	 * @param from
	 *            the start number of clicks to retrieve.
	 * @param to
	 *            the stop number of clicks to retrieve.
	 * @param dateFrom
	 *            the start date.
	 * @param dateTo
	 *            the end date.
	 * @param userAgent
	 *            the user agent from which clicks comes.
	 * @return a {@link DAOResponse} object containing the operation's response.
	 */
	public DAOResponse getStatisticsDateUserAgent(String url, int from, int to,
			Date dateFrom, Date dateTo, String userAgent);

}
