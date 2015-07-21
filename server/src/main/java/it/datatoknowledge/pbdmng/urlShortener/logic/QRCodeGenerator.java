
package it.datatoknowledge.pbdmng.urlShortener.logic;

import it.datatoknowledge.pbdmng.urlShortener.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

/**
 * 
 * This class provides utility to generate a QRCode containing an URL.
 * @author Gianluca Colaianni
 * 
 */
public class QRCodeGenerator extends Base {
	
	private final static int DEFAULT_HEIGHT = 175;
	private final static int DEFAULT_WIDTH = 175;

	/**
	 * Generates a new QRCode image containing the specified url and save it on the specified filePath.
	 * The image size is specified with the width and height property.
	 * The generated QRCode has an high error level to be able to get info also with image damages.
	 * The file is created only if it not already exists.
	 * @param url
	 * @param filePath
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 * @throws InvalidPathException
	 * @throws NullPointerException
	 */
	public static String createQRCode(String url, String filePath, int width, int height) throws IOException, InvalidPathException, NullPointerException {
		String result = null;
		ByteArrayOutputStream out = QRCode.from(url).withErrorCorrection(ErrorCorrectionLevel.H).withSize(width, height).stream();
		StringBuffer toWrite = new StringBuffer(filePath);
		toWrite.append(Constants.DOT);
		toWrite.append(ImageType.PNG);
		
		if (out != null) {
			int writedbytes = Files.newByteChannel(Paths.get(toWrite.toString()), EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.READ, StandardOpenOption.WRITE)).write(ByteBuffer.wrap(out.toByteArray()));
			if (writedbytes > BigInteger.ZERO.intValue()) {
				result = toWrite.toString();
			}
			out.close();
		}
		return result;
	}
	
	/**
	 * Generates a new QRCode image with default value.
	 * @see #createQRCode(String, String, int, int)
	 * @param url
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws InvalidPathException
	 * @throws NullPointerException
	 */
	public static String createQRCode(String url, String filePath) throws IOException, InvalidPathException, NullPointerException {
		return createQRCode(url, filePath, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
}
