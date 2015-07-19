package it.datatoknowledge.pbdmng.urlShortener.logic;

import it.datatoknowledge.pbdmng.urlShortener.utils.Constants;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

/**
 * Tiny generator.
 * @author gaetano
 *
 */
public class TinyGenerator extends Base {
	
	private final static String HASH_ALORITHM = "SHA-512";

	private final static String INVALID_SYMBOL = "/";
	
	private final static String REPLACE_SYMBOL = "@";

	public TinyGenerator() {
		super();
	}

	/**
	 * Calculate a short.
	 * @param url - long url
	 * @return tinyUrl
	 */
	public String getTiny(String url) {
		String result = null;
		if (url != null) {
			if (url.length() > 0) {
				try {
					Random r = new Random();
					byte[] urlBytes = url.getBytes(Constants.DEFAULT_ENCODING);
					byte[] random = new byte[urlBytes.length];
					r.nextBytes(random);
					byte[] resultByte = new byte[urlBytes.length];
					for (int i = 0; i < urlBytes.length; i++) {
						resultByte[i] = (byte) (urlBytes[i] + random[i]);
					}
					MessageDigest md;
					byte[] hashBytes = null;
					md = MessageDigest.getInstance(HASH_ALORITHM);

					hashBytes = md.digest(resultByte);
					hashBytes = Arrays.copyOf(hashBytes, 6);
					result = Base64.getUrlEncoder().encodeToString(hashBytes);
					result = result.replaceAll(INVALID_SYMBOL, REPLACE_SYMBOL);

				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					error(loggingId, " - Unknow hash algorithm.");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					error(loggingId, " - Byte encoding not supported");
				}
			} else {
				error(loggingId, "Url length not valid.");
			}
		} else {
			error(loggingId, "Url not accepted");
		}
		return result;
	}

}
