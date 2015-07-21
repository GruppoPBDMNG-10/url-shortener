/*
 * QRCodeHandler.java
 * 
 * 21/lug/2015
 */
package it.datatoknowledge.pbdmng.urlShortener.logic;

import static spark.Spark.get;
import it.datatoknowledge.pbdmng.urlShortener.utils.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import spark.Request;

/**
 * 
 * Handler for QRCode images.
 * @author Gianluca Colaianni
 * 
 */
public class QRCodeHandler extends Base implements CommonService{

	/**
	 * 
	 */
	public QRCodeHandler() {
		// TODO Auto-generated constructor stub
		super();
	}

	/* (non-Jsdoc)
	 * @see it.datatoknowledge.pbdmng.urlShortener.logic.CommonService#process(spark.Request)
	 */
	@Override
	public String process(Request clientRequest) {
		// TODO Auto-generated method stub
		String image = clientRequest.params(":image");
		StringBuffer buffer = new StringBuffer(serviceParameters.getValue(Parameters.IMAGES_PATH, Parameters.DEFAULT_IMAGES_PATH));
		buffer.append(image);
		return buffer.toString();
	}

	/* (non-Jsdoc)
	 * @see it.datatoknowledge.pbdmng.urlShortener.logic.CommonService#exposeServices()
	 */
	@Override
	public void exposeServices() {
		// TODO Auto-generated method stub
		info(loggingId,"Exposed QRCodeHandler");
		String route = serviceParameters.getValue(Parameters.ROUTE_IMAGES, Parameters.DEFAULT_ROUTE_IMAGES);
		get(route, (request, response) -> {
			 String path = process(request);
			 byte[] bytes = null;
			 try {
				 bytes = Files.readAllBytes(Paths.get(path));
			 } catch (IOException | OutOfMemoryError | SecurityException  e) {
				 error(loggingId, e, "Error during image loading file");
			 }
			
			 if (bytes != null) {
				 HttpServletResponse raw = response.raw();
				 ServletOutputStream out = null;
				 try {
					 out =  raw.getOutputStream();
					 out.write(bytes);
					 out.flush(); 
				 } catch(IOException | IllegalStateException  e) {
					 error(loggingId, e, "Error during image output servlet");
				 } finally {
					 if (out != null) {
						 try {
							 out.close();
						 } catch (IOException e) {
							 //nothing to do
						 }
					 }
				 }
			 }
			
			 return response.raw();
		 });
	}

}
