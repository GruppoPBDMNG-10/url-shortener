package it.datatoknowledge.pbdmng.urlShortener.test;

import it.datatoknowledge.pbdmng.urlShortener.bean.url.UrlRequest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

public class ClientTest {

	public static void main(String... args) {
		UrlRequest request = new UrlRequest();
		request.setUrl("www.faceboooooook.it");
		String jsonRequest = new Gson().toJson(request);
		System.out.println("Data to write: " + jsonRequest);

		try {
			URL url = new URL("http://localhost:4567/tiny");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
		
			
			con.setRequestProperty("Content-Type", "application/json");
//			con.setRequestProperty("charset", "UTF-8");
//			con.connect();
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(jsonRequest);
			System.out.println("Done!!\n");

			Reader in = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			for (int c = in.read(); c != -1; c = in.read())
				System.out.print((char) c);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
