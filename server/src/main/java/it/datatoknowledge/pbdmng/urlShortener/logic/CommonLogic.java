package it.datatoknowledge.pbdmng.urlShortener.logic;

import it.datatoknowledge.pbdmng.urlShortener.utils.Constants;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public abstract class CommonLogic extends Base{
	
	protected CommonLogic() {
		super();
	}
	
	protected String getTiny(String url, String custom) {
		String result = null;
		if (custom != null) {
			//TODO: CHECK FOR CUSTOM
			info(loggingId, "Desired custom:", custom);
			
			result = custom;
		} else {
			result = generateTiny(url);
		}
		return result;
	}

	private String generateTiny(String url) {
		
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
					md = MessageDigest.getInstance("SHA-512");

					hashBytes = md.digest(resultByte);
					hashBytes = Arrays.copyOf(hashBytes, 6);
					result = Base64.getUrlEncoder().encodeToString(hashBytes);
					

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
