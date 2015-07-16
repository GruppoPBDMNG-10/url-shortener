package it.datatoknowledge.pbdmng.urlShortener.dao.jedis;

import it.datatoknowledge.pbdmng.urlShortener.dao.DAOInterface;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOResponse;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOResponseCode;
import it.datatoknowledge.pbdmng.urlShortener.dao.DAOTransferKey;
import it.datatoknowledge.pbdmng.urlShortener.geoLocation.GeoLocationService;
import it.datatoknowledge.pbdmng.urlShortener.logic.Base;
import it.datatoknowledge.pbdmng.urlShortener.utils.Constants;
import it.datatoknowledge.pbdmng.urlShortener.utils.Utility;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class DAOJedis extends Base implements DAOInterface{

	private Jedis conn;
	
	private Utility utility;

	private final static String OK = "OK";

	private final static String CLICKS_LIST = "CLICKS_LIST:";

	public DAOJedis(Jedis jedisConn) {
		super();
		conn = jedisConn;
		utility = new Utility();
	}

	private boolean checkKey(String key) {
		boolean isOk = true;

		try {
			isOk = conn.exists(key.getBytes(Constants.DEFAULT_ENCODING));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			error(e, loggingId, "Check key error during encoding");
		}

		return !isOk;
	}

	public DAOResponse newUrl(String key, String originalUrl, Date timestamp) {
		DAOResponse result = new DAOResponse();
		DAOResponseCode resultCode = DAOResponseCode.NOT_INSERTED;
		if (checkKey(key)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(Keys.LONG_URL, originalUrl);
			map.put(Keys.TIMESTAMP, utility.dateToString(timestamp, Constants.DATE_PATTERN_TIMESTAMP_CENTS));
			resultCode = conn.hmset(key, map).equals(OK) ? DAOResponseCode.INSERTED
					: DAOResponseCode.NOT_INSERTED;
		} else {
			resultCode = DAOResponseCode.DUPLICATED_KEY;
		}
		result.setResultCode(resultCode);
		return result;
	}

	public DAOResponse getOrigin(String url, String ip, String userAgent,
			Date timestamp) {
		DAOResponse result = new DAOResponse();
		result.setResultCode(DAOResponseCode.NOT_MAPPED);
		String redirect = conn.hget(url, Keys.LONG_URL);
		GeoLocationService geoService = new GeoLocationService(ip);
		String jsonLocationInfo = geoService.getJsonInfo();
		if (redirect != null) {
			// update click count
			long clickId = conn.incr(Keys.NEXT_CLICK_ID);
			Pipeline p = conn.pipelined();
			p.multi(); // start a transaction
			Map<String, String> clickMap = new HashMap<String, String>();
			String stringTimestamp = utility.dateToString(timestamp, Constants.DATE_PATTERN_TIMESTAMP_DATE_ONLY);
			clickMap.put(Keys.TIMESTAMP, stringTimestamp);
			clickMap.put(Keys.USER_AGENT, userAgent);
			clickMap.put(Keys.IP, ip);
			if (jsonLocationInfo != null) {
				clickMap.put(Keys.LOCATION_INFO, jsonLocationInfo);	
			}
			p.hmset(String.valueOf(clickId), clickMap);
			StringBuffer baseBuffer = new StringBuffer(CLICKS_LIST);
			baseBuffer.append(url);
			double doubleTimestamp = Double.valueOf(stringTimestamp)
					.doubleValue();
			String stringClickId = String.valueOf(clickId);
			System.out.println("Base buffer: " + baseBuffer);
			p.zadd(baseBuffer.toString(), doubleTimestamp, stringClickId);
			baseBuffer.append(Constants.COLON);
			StringBuffer ipBuffer = new StringBuffer(baseBuffer);
			ipBuffer.append(ip);
			System.out.println("Ip buffer: " + ipBuffer);
			p.zadd(ipBuffer.toString(), doubleTimestamp, stringClickId);
			ipBuffer.append(Constants.COLON);
			ipBuffer.append(userAgent.toLowerCase());
			System.out.println("IpAgent buffer: " + ipBuffer);
			p.zadd(ipBuffer.toString(), doubleTimestamp, stringClickId);
			StringBuffer userAgentBuffer = new StringBuffer(baseBuffer);
			baseBuffer.append(Constants.COLON);
			userAgentBuffer.append(userAgent.toLowerCase());
			System.out.println("UserAgent buffer: " + userAgentBuffer);
			p.zadd(userAgentBuffer.toString(), doubleTimestamp, stringClickId);
			p.exec();
			p.sync();
			result.setResponse(redirect);
			result.setResultCode(DAOResponseCode.UPDATED);
		}
		return result;
	}

	/**
	 * Retrieve statistics for the specified url with (to - from) related
	 * clicks.
	 * 
	 * @param url
	 *            the short-url.
	 * @param from
	 *            the required start click.
	 * @param to
	 *            the required end click.
	 * @return a {@link DAOResponse} with the statistics.
	 */
	public DAOResponse getStatistics(String url, int from, int to) {
		DAOResponse result = new DAOResponse();
		result.setResultCode(DAOResponseCode.NOT_MAPPED);
		StringBuffer buffer = new StringBuffer(CLICKS_LIST);
		buffer.append(url);
		Pipeline p = conn.pipelined();
		Response<Map<String, String>> map = p.hgetAll(url);
		Response<Long> totalClicks = p.zcard(buffer.toString());
		Response<Set<String>> idClicksResponse = p.zrevrange(buffer.toString(),
				from, to);
		p.sync();
		if (map.get() != null) {
			result.setResultCode(DAOResponseCode.OK);
			List<Map<String, Object>> clicks = getClicks(idClicksResponse, (to - from));
			HashMap<String, Object> response = new HashMap<String, Object>();
			response.put(DAOTransferKey.URL_SHORT, map.get());
			response.put(DAOTransferKey.TOTAL_CLICKS, totalClicks.get());
			response.put(DAOTransferKey.CLICKS, clicks);
			result.setResponse(response);
		}
		return result;
	}
	
	public DAOResponse getStatisticsIpUserAgent(String url, int from, int to, String ip, String userAgent) {
		DAOResponse result = new DAOResponse();
		result.setResultCode(DAOResponseCode.NOT_MAPPED);
		StringBuffer buffer = new StringBuffer(CLICKS_LIST);
		buffer.append(url);
		Pipeline p = conn.pipelined();
		Response<Map<String, String>> map = p.hgetAll(url);
		Response<Long> totalClicks = p.zcard(buffer.toString());
		buffer.append(Constants.COLON);
		buffer.append(ip);
		buffer.append(Constants.COLON);
		buffer.append(userAgent.toLowerCase());
		Response<Long> ipAgentTotalClicks = p.zcard(buffer.toString());
		Response<Set<String>> idClicksResponse = p.zrevrange(buffer.toString(),
				from, to);
		p.sync();
		if (map.get() != null) {
			result.setResultCode(DAOResponseCode.OK);
			List<Map<String, Object>> clicks = getClicks(idClicksResponse, (to - from));
			HashMap<String, Object> response = new HashMap<String, Object>();
			response.put(DAOTransferKey.URL_SHORT, map.get());
			response.put(DAOTransferKey.TOTAL_CLICKS, totalClicks.get());
			response.put(DAOTransferKey.IP_AGENT_TOTAL_CLICKS, ipAgentTotalClicks.get());
			response.put(DAOTransferKey.IP_AGENT_CLICKS, clicks);
			result.setResponse(response);
		}
		return result;
	}
	
	public DAOResponse getStatisticsIp(String url, int from, int to, String ip) {
		DAOResponse result = new DAOResponse();
		result.setResultCode(DAOResponseCode.NOT_MAPPED);
		StringBuffer buffer = new StringBuffer(CLICKS_LIST);
		buffer.append(url);
		Pipeline p = conn.pipelined();
		Response<Map<String, String>> map = p.hgetAll(url);
		Response<Long> totalClicks = p.zcard(buffer.toString());
		buffer.append(Constants.COLON);
		buffer.append(ip);
		System.out.println("Get ip buffer: " + buffer);
		Response<Long> ipTotalClicks = p.zcard(buffer.toString());
		Response<Set<String>> idClicksResponse = p.zrevrange(buffer.toString(),
				from, to);
		p.sync();
		if (map.get() != null) {
			result.setResultCode(DAOResponseCode.OK);
			List<Map<String, Object>> clicks = getClicks(idClicksResponse, (to - from));
			HashMap<String, Object> response = new HashMap<String, Object>();
			response.put(DAOTransferKey.URL_SHORT, map.get());
			response.put(DAOTransferKey.TOTAL_CLICKS, totalClicks.get());
			response.put(DAOTransferKey.IP_TOTAL_CLICKS, ipTotalClicks.get());
			response.put(DAOTransferKey.IP_CLICKS, clicks);
			result.setResponse(response);
		}
		return result;
	}
	
	public DAOResponse getStatisticsUserAgent(String url, int from, int to, String userAgent) {
		DAOResponse result = new DAOResponse();
		result.setResultCode(DAOResponseCode.NOT_MAPPED);
		StringBuffer buffer = new StringBuffer(CLICKS_LIST);
		buffer.append(url);
		Pipeline p = conn.pipelined();
		Response<Map<String, String>> map = p.hgetAll(url);
		Response<Long> totalClicks = p.zcard(buffer.toString());
		buffer.append(Constants.COLON);
		buffer.append(userAgent.toLowerCase());
		Response<Long> agentTotalClicks = p.zcard(buffer.toString());
		Response<Set<String>> idClicksResponse = p.zrevrange(buffer.toString(),
				from, to);
		p.sync();
		if (map.get() != null) {
			result.setResultCode(DAOResponseCode.OK);
			List<Map<String, Object>> clicks = getClicks(idClicksResponse, (to - from));
			HashMap<String, Object> response = new HashMap<String, Object>();
			response.put(DAOTransferKey.URL_SHORT, map.get());
			response.put(DAOTransferKey.TOTAL_CLICKS, totalClicks.get());
			response.put(DAOTransferKey.AGENT_TOTAL_CLICKS, agentTotalClicks.get());
			response.put(DAOTransferKey.AGENT_CLICKS, clicks);
			result.setResponse(response);
		}
		return result;
	}

	public DAOResponse getStatisticsDate(String url, int from, int to,
			Date dateFrom, Date dateTo) {
		DAOResponse result = new DAOResponse();
		result.setResultCode(DAOResponseCode.NOT_MAPPED);
		StringBuffer buffer = new StringBuffer(CLICKS_LIST);
		buffer.append(url);
		double doubleDateFrom = Double.valueOf(utility.dateToString(dateFrom, Constants.DATE_PATTERN_TIMESTAMP_DATE_ONLY));
		double doubleDateTo = Double.valueOf(utility.dateToString(dateTo, Constants.DATE_PATTERN_TIMESTAMP_DATE_ONLY));
		Pipeline p = conn.pipelined();
		Response<Map<String, String>> map = p.hgetAll(url);
		Response<Long> totalClicks = p.zcard(buffer.toString());
		Response<Set<String>> idClicksResponse = p.zrevrangeByScore(
				buffer.toString(), doubleDateFrom, doubleDateTo);
		p.sync();
		if (map.get() != null) {
			result.setResultCode(DAOResponseCode.OK);
			List<Map<String, Object>> clicks = getClicks(idClicksResponse, (to - from));
			HashMap<String, Object> response = new HashMap<String, Object>();
			response.put(DAOTransferKey.URL_SHORT, map.get());
			response.put(DAOTransferKey.TOTAL_CLICKS, totalClicks.get());
			response.put(DAOTransferKey.CLICKS, clicks);
			result.setResponse(response);
		}
		return result;
	}
	
	public DAOResponse getStatisticsDateIpUserAgent(String url, int from, int to, Date dateFrom, Date dateTo, String ip, String userAgent) {
		DAOResponse result = new DAOResponse();
		result.setResultCode(DAOResponseCode.NOT_MAPPED);
		StringBuffer buffer = new StringBuffer(CLICKS_LIST);
		buffer.append(url);
		double doubleDateFrom = Double.valueOf(utility.dateToString(dateFrom, Constants.DATE_PATTERN_TIMESTAMP_DATE_ONLY));
		System.out.println(doubleDateFrom);
		double doubleDateTo = Double.valueOf(utility.dateToString(dateTo, Constants.DATE_PATTERN_TIMESTAMP_DATE_ONLY));
		System.out.println(doubleDateTo);
		Pipeline p = conn.pipelined();
		Response<Map<String, String>> map = p.hgetAll(url);
		Response<Long> totalClicks = p.zcard(buffer.toString());
		buffer.append(Constants.COLON);
		buffer.append(ip);
		buffer.append(Constants.COLON);
		buffer.append(userAgent.toLowerCase());
		Response<Long> ipAgentTotalClicks = p.zcard(buffer.toString());
		Response<Set<String>> idClicksResponse = p.zrevrangeByScore(
				buffer.toString(), doubleDateFrom, doubleDateTo);
		p.sync();
		if (map.get() != null) {
			result.setResultCode(DAOResponseCode.OK);
			List<Map<String, Object>> clicks = getClicks(idClicksResponse, (to - from));
			HashMap<String, Object> response = new HashMap<String, Object>();
			response.put(DAOTransferKey.URL_SHORT, map.get());
			response.put(DAOTransferKey.TOTAL_CLICKS, totalClicks.get());
			response.put(DAOTransferKey.IP_AGENT_TOTAL_CLICKS, ipAgentTotalClicks.get());
			response.put(DAOTransferKey.IP_AGENT_CLICKS, clicks);
			result.setResponse(response);
		}
		return result;
	}
	
	public DAOResponse getStatisticsDateIp(String url, int from, int to, Date dateFrom, Date dateTo, String ip) {
		DAOResponse result = new DAOResponse();
		result.setResultCode(DAOResponseCode.NOT_MAPPED);
		StringBuffer buffer = new StringBuffer(CLICKS_LIST);
		buffer.append(url);
		double doubleDateFrom = Double.valueOf(utility.dateToString(dateFrom, Constants.DATE_PATTERN_TIMESTAMP_DATE_ONLY));
		double doubleDateTo = Double.valueOf(utility.dateToString(dateTo, Constants.DATE_PATTERN_TIMESTAMP_DATE_ONLY));
		Pipeline p = conn.pipelined();
		Response<Map<String, String>> map = p.hgetAll(url);
		Response<Long> totalClicks = p.zcard(buffer.toString());
		buffer.append(Constants.COLON);
		buffer.append(ip);
		Response<Long> ipTotalClicks = p.zcard(buffer.toString());
		Response<Set<String>> idClicksResponse = p.zrevrangeByScore(
				buffer.toString(), doubleDateFrom, doubleDateTo);
		p.sync();
		if (map.get() != null) {
			result.setResultCode(DAOResponseCode.OK);
			List<Map<String, Object>> clicks = getClicks(idClicksResponse, (to - from));
			HashMap<String, Object> response = new HashMap<String, Object>();
			response.put(DAOTransferKey.URL_SHORT, map.get());
			response.put(DAOTransferKey.TOTAL_CLICKS, totalClicks.get());
			response.put(DAOTransferKey.IP_TOTAL_CLICKS, ipTotalClicks.get());
			response.put(DAOTransferKey.IP_CLICKS, clicks);
			result.setResponse(response);
		}
		return result;
	}
	
	public DAOResponse getStatisticsDateUserAgent(String url, int from, int to, Date dateFrom, Date dateTo, String userAgent) {
		DAOResponse result = new DAOResponse();
		result.setResultCode(DAOResponseCode.NOT_MAPPED);
		StringBuffer buffer = new StringBuffer(CLICKS_LIST);
		buffer.append(url);
		double doubleDateFrom = Double.valueOf(utility.dateToString(dateFrom, Constants.DATE_PATTERN_TIMESTAMP_DATE_ONLY));
		double doubleDateTo = Double.valueOf(utility.dateToString(dateTo, Constants.DATE_PATTERN_TIMESTAMP_DATE_ONLY));
		Pipeline p = conn.pipelined();
		Response<Map<String, String>> map = p.hgetAll(url);
		Response<Long> totalClicks = p.zcard(buffer.toString());
		buffer.append(Constants.COLON);
		buffer.append(userAgent.toLowerCase());
		Response<Long> agentTotalClicks = p.zcard(buffer.toString());
		Response<Set<String>> idClicksResponse = p.zrevrangeByScore(
				buffer.toString(), doubleDateFrom, doubleDateTo);
		p.sync();
		if (map.get() != null) {
			result.setResultCode(DAOResponseCode.OK);
			List<Map<String, Object>> clicks = getClicks(idClicksResponse, (to - from));
			HashMap<String, Object> response = new HashMap<String, Object>();
			response.put(DAOTransferKey.URL_SHORT, map.get());
			response.put(DAOTransferKey.TOTAL_CLICKS, totalClicks.get());
			response.put(DAOTransferKey.AGENT_TOTAL_CLICKS, agentTotalClicks.get());
			response.put(DAOTransferKey.AGENT_CLICKS, clicks);
			result.setResponse(response);
		}
		return result;
	}

	/**
	 * Retrieve the clicks value from the clicksId present into the
	 * {@link Response} idClickResponse.
	 * 
	 * @param idClicksResponse
	 *            value or reference.
	 * 
	 * @param tot
	 *            the total desired number of elements
	 * @return a {@link List} of {@link Map} of clicks.
	 */
	private List<Map<String, Object>> getClicks(
			Response<Set<String>> idClicksResponse, int tot) {
		List<Response<Map<String, String>>> clicksResponse = new ArrayList<Response<Map<String, String>>>(
				idClicksResponse.get().size());
		Pipeline p = conn.pipelined();
		int i = 0;
		for (String clickId : idClicksResponse.get()) {
			Response<Map<String, String>> click = p.hgetAll(clickId);
			clicksResponse.add(click);
			i++;
			if (i >= tot) {
				break;
			}
		}
		p.sync();

		List<Map<String, Object>> clicks = new ArrayList<Map<String, Object>>(
				clicksResponse.size());
		Iterator<String> it = idClicksResponse.get().iterator();
		for (Response<Map<String, String>> clickResponse : clicksResponse) {
			Map<String, String> click = clickResponse.get();
			Map<String, Object> clickToSet = new HashMap<String, Object>();
			clickToSet.put(Keys.ID, it.next());
			for (Map.Entry<String, String> entry : click.entrySet()) {
				String key = entry.getKey();
				Object valueToSet = null;
				if (key.equals(Keys.TIMESTAMP)) {
					String value = entry.getValue();
					valueToSet = utility.stringToDate(value);
				} else if (key.equals(Keys.IP) || key.equals(Keys.USER_AGENT)) {
					valueToSet = entry.getValue();
				}
				if (valueToSet != null) {
					clickToSet.put(key, valueToSet);
				}
			}
			clicks.add(clickToSet);
		}
		return clicks;
	}
	
}
