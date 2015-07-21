package it.datatoknowledge.pbdmng.urlShortener.logic;

import static spark.Spark.get;
import it.datatoknowledge.pbdmng.urlShortener.bean.Result;
import it.datatoknowledge.pbdmng.urlShortener.bean.url.AgentStatistics;
import it.datatoknowledge.pbdmng.urlShortener.bean.url.Click;
import it.datatoknowledge.pbdmng.urlShortener.bean.url.IpAgentStatistics;
import it.datatoknowledge.pbdmng.urlShortener.bean.url.IpStatistics;
import it.datatoknowledge.pbdmng.urlShortener.bean.url.Statistics;
import it.datatoknowledge.pbdmng.urlShortener.bean.url.UrlResponse;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOFactory;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOImplementation;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOInterface;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOResponse;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOTransferKey;
import it.datatoknowledge.pbdmng.urlShortener.dao.jedis.Keys;
import it.datatoknowledge.pbdmng.urlShortener.json.JsonManager;
import it.datatoknowledge.pbdmng.urlShortener.utils.Parameters;
import it.datatoknowledge.pbdmng.urlShortener.utils.Utility;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import spark.Request;

/**
 * Statistics handler.
 * @author gaetano
 *
 */
public class StatisticsHandler extends Base implements CommonService {

	private final static String TINY = "tiny";
	private final static String FROM = "from";
	private final static String TO = "to";
	private final static String IP = "ip";
	private final static String DATE_FROM = "date_from";
	private final static String DATE_TO = "date_to";
	private final static String USER_AGENT = "userAgent";
	private final static int DEFAULT_FROM = 0;
	private Utility utility;

	public StatisticsHandler() {
		// TODO Auto-generated constructor stub
		super();
		utility = new Utility();
	}

	/**
	 * Check the value of dateTo. If dateFrom is valued and dateTo isn't valued,
	 * set the current date.
	 * 
	 * @param dateFrom
	 *            value or reference.
	 * @param dateTo
	 *            value or reference
	 * @return dateTo if valued, the currentDate otherwise.
	 */
	private Date checkDate(Date dateFrom, Date dateTo) {
		Date result = dateTo;
		if (dateFrom != null) {
			if (dateTo == null) {
				result = new Date();
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String process(Request clientRequest) {
		// TODO Auto-generated method stub
		debug(loggingId, "/*** Start Statistics.process ***/");
		long start = System.currentTimeMillis();
		String result = null;
		UrlResponse response = new UrlResponse();
		Result resultResponse = new Result();
		resultResponse.setDescription(Result.GENERIC_ERROR_DESCRIPTION);
		resultResponse.setReturnCode(Result.GENERIC_ERROR_RETURN_CODE);
		response.setResult(resultResponse);

		String tiny = clientRequest.queryParams(TINY);
		if (tiny != null) {
			response.setUrlTiny(tiny);
			int defaultTo = Integer.valueOf(
					serviceParameters.getValue(Parameters.TO_VALUE_STATISTICS,
							Parameters.DEFAULT_TO_VALUE_STATISTICS)).intValue();
			int from = clientRequest.queryParams(FROM) != null ? Integer
					.valueOf(clientRequest.queryParams(FROM)).intValue()
					: DEFAULT_FROM;
			int to = clientRequest.queryParams(TO) != null ? Integer.valueOf(
					clientRequest.queryParams(TO)).intValue() : defaultTo;
			String ip = clientRequest.queryParams(IP);
			String userAgent = clientRequest.queryParams(USER_AGENT);
			Date dateFrom = utility.stringToDate(clientRequest
					.queryParams(DATE_FROM));
			Date dateTo = utility.stringToDate(clientRequest
					.queryParams(DATE_TO));
			info(loggingId, "Filter:", "ip =", ip, "agent =", userAgent,
					"from =", from, "to =", to, "Date from =", dateFrom,
					"Date to =", dateTo);
			dateTo = checkDate(dateFrom, dateTo);
			DAOResponse responseDao;
			if (dateFrom != null) {
				responseDao = processWithDate(tiny, from, to, ip, userAgent,
						dateFrom, dateTo);
			} else {
				responseDao = processWithoutDate(tiny, from, to, ip, userAgent);
			}
			switch (responseDao.getResultCode()) {

			case OK:
				resultResponse.setReturnCode(Result.OK_RETURN_CODE);
				resultResponse.setDescription(Result.OK_DESCRIPTION);
				Map<String, Object> data = (Map<String, Object>) responseDao
						.getResponse();
				Map<String, String> urlShort = (Map<String, String>) data
						.get(DAOTransferKey.URL_SHORT);
				String longUrl = urlShort.get(Keys.LONG_URL);
				Date urlTimestamp = utility.stringToDate(urlShort
						.get(Keys.TIMESTAMP));
				response.setUrl(longUrl);
				Statistics statistics = new Statistics();
				response.setStatistics(statistics);
				statistics.setInserted(urlTimestamp);

				Long totalClicks = (Long) data.get(DAOTransferKey.TOTAL_CLICKS);
				Long ipTotalClicks = (Long) data
						.get(DAOTransferKey.IP_TOTAL_CLICKS);
				Long agentTotalClicks = (Long) data
						.get(DAOTransferKey.AGENT_TOTAL_CLICKS);
				Long ipAgentTotalClicks = (Long) data
						.get(DAOTransferKey.IP_AGENT_TOTAL_CLICKS);
				List<Map<String, Object>> clicksMap;
				List<Click> clicks;

				statistics.setNumClicks(totalClicks.longValue());
				clicksMap = (List<Map<String, Object>>) data
						.get(DAOTransferKey.CLICKS);
				clicks = getClicks(clicksMap);
				statistics.setClicks(clicks);
				if (ipTotalClicks != null) {
					clicksMap = (List<Map<String, Object>>) data
							.get(DAOTransferKey.IP_CLICKS);
					clicks = getClicks(clicksMap);
					IpStatistics ipStat = new IpStatistics();
					ipStat.setClicks(clicks);
					ipStat.setIp(ip);
					ipStat.setNumClicks(ipTotalClicks.longValue());
					statistics.setIpStat(ipStat);
				} else if (agentTotalClicks != null) {
					clicksMap = (List<Map<String, Object>>) data
							.get(DAOTransferKey.AGENT_CLICKS);
					clicks = getClicks(clicksMap);
					AgentStatistics agentStat = new AgentStatistics();
					statistics.setAgentsStat(agentStat);
					agentStat.setAgent(userAgent);
					agentStat.setNumClicks(agentTotalClicks.longValue());
					agentStat.setClicks(clicks);
				} else if (ipAgentTotalClicks != null) {
					clicksMap = (List<Map<String, Object>>) data
							.get(DAOTransferKey.IP_AGENT_CLICKS);
					clicks = getClicks(clicksMap);
					IpAgentStatistics ipAgentStat = new IpAgentStatistics();
					statistics.setIpAgentStat(ipAgentStat);
					ipAgentStat.setNumClicks(ipAgentTotalClicks.longValue());
					ipAgentStat.setAgent(userAgent);
					ipAgentStat.setIp(ip);
					ipAgentStat.setClicks(clicks);
				}
				Click lastClick = (clicks != null && clicks.size() > BigInteger.ZERO
						.intValue()) ? clicks.get(BigInteger.ZERO.intValue())
						: null;
				statistics.setLastClick(lastClick);
				break;
			case NOT_MAPPED:
				resultResponse.setReturnCode(Result.NOT_FOUND);
				resultResponse.setDescription(Result.NOT_FOUND_DESCRIPTION);
				break;
			default:
				break;

			}
		} else {
			error(loggingId, "Tiny url not present into query");
		}
		result = JsonManager.buildJson(response);
		long finish = System.currentTimeMillis();
		info(loggingId, " - Elapsed time: " + (finish - start));
		debug(loggingId, "/*** Finish Statistics.process ***/");
		info(loggingId, "Response:", result);
		return result;
	}

	/**
	 * Transform a {@link List} of {@link Map} representing {@link Click} object
	 * into {@link Click}.
	 * 
	 * @param clicksMap
	 *            the {@link List} of {@link Map}.
	 * @return a {@link List} of {@link Click}'s object.
	 */
	private List<Click> getClicks(List<Map<String, Object>> clicksMap) {
		List<Click> clicks = null;
		if (clicksMap != null) {
			clicks = new ArrayList<Click>(clicksMap.size());
			for (Map<String, Object> clickMap : clicksMap) {
				String clickIp = (String) clickMap.get(Keys.IP);
				String clickAgent = (String) clickMap.get(Keys.USER_AGENT);
				String locationInfo = (String) clickMap.get(Keys.LOCATION_INFO);
				Date timestamp = (Date) clickMap.get(Keys.TIMESTAMP);
				Click click = new Click();
				click.setIp(clickIp);
				click.setTimestamp(timestamp);
				click.setUserAgent(clickAgent);
				click.setLocationInfo(locationInfo);
				clicks.add(click);
			}
		} else {
			info(loggingId, "No clicks found");
		}

		return clicks;
	}

	private DAOResponse processWithDate(String url, int from, int to,
			String ip, String userAgent, Date dateFrom, Date dateTo) {
		info(loggingId, "Process with date");
		DAOResponse result = null;
		DAOInterface dao = DAOFactory.getIstance(DAOImplementation.JEDIS);
		if (ip != null) {
			if (userAgent != null) {
				result = dao.getStatisticsDateIpUserAgent(url, from, to,
						dateFrom, dateTo, ip, userAgent);
			} else {
				result = dao.getStatisticsDateIp(url, from, to, dateFrom,
						dateTo, ip);
			}
		} else if (userAgent != null) {
			result = dao.getStatisticsDateUserAgent(url, from, to, dateFrom,
					dateTo, userAgent);
		} else {
			result = dao.getStatisticsDate(url, from, to, dateFrom, dateTo);
		}
		return result;
	}

	private DAOResponse processWithoutDate(String url, int from, int to,
			String ip, String userAgent) {
		info(loggingId, "Process without date");
		DAOResponse result = null;
		DAOInterface dao = DAOFactory.getIstance(DAOImplementation.JEDIS);
		if (ip != null) {
			if (userAgent != null) {
				result = dao.getStatisticsIpUserAgent(url, from, to, ip,
						userAgent);
			} else {
				result = dao.getStatisticsIp(url, from, to, ip);
			}
		} else if (userAgent != null) {
			result = dao.getStatisticsUserAgent(url, from, to, userAgent);
		} else {
			result = dao.getStatistics(url, from, to);
		}
		return result;
	}

	@Override
	public void exposeServices() {
		// TODO Auto-generated method stub
		info(loggingId,"Exposed StatisticsHandler");
		String route = serviceParameters.getValue(Parameters.ROUTE_STATISTICS,
				Parameters.DEFAULT_ROUTE_STATISTICS);
		get(route, (req, res) -> {
			return process(req);
		});
	}

}
