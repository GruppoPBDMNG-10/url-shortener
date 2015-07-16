package it.datatoknowledge.pbdmng.urlShortener.dao;

import java.util.Date;

public interface DAOInterface {
	
	public DAOResponse newUrl(String key, String originalUrl, Date timestamp);
	
	public DAOResponse getOrigin(String url, String ip, String userAgent, Date timestamp);
	
	public DAOResponse getStatistics(String url, int from, int to);
	
	public DAOResponse getStatisticsIp(String url, int from, int to, String ip);
	
	public DAOResponse getStatisticsIpUserAgent(String url, int from, int to, String ip, String userAgent);
	
	public DAOResponse getStatisticsUserAgent(String url, int from, int to, String userAgent);
	
	public DAOResponse getStatisticsDate(String url, int from, int to, Date dateFrom, Date dateTo);
	
	public DAOResponse getStatisticsDateIp(String url, int from, int to, Date dateFrom, Date dateTo, String ip);
	
	public DAOResponse getStatisticsDateIpUserAgent(String url, int from, int to, Date dateFrom, Date dateTo, String ip, String userAgent);
	
	public DAOResponse getStatisticsDateUserAgent(String url, int from, int to, Date dateFrom, Date dateTo, String userAgent);

}
