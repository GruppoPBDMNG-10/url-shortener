package it.datatoknowledge.pbdmng.urlShortener.bean;

/**
 * 
 * @author Gianluca  Colaianni.
 * Contains information about the processing result.
 */
public class Result {
	
	/**
	 * Processing ok.
	 */
	public final static int OK_RETURN_CODE = 0;
	
	/**
	 * Processing ok.
	 */
	public final static String OK_DESCRIPTION = "Processed completed";
	
	/**
	 * Processing with generic error.
	 */
	public final static int  GENERIC_ERROR_RETURN_CODE = -1;
	
	/**
	 * Processing with generic error.
	 */
	public final static String GENERIC_ERROR_DESCRIPTION = "An error as occourred";
	
	/**
	 * Duplicated custom required.
	 */
	public final static int  DUPLICATED_ERROR_RETURN_CODE = -2;
	
	/**
	 * Duplicated custom required.
	 */
	public final static String DUPLICATED_ERROR_DESCRIPTION = "Custom url not available";
	
	/**
	 * Tiny url not mapped.
	 */
	public final static int NOT_FOUND = -3;
	
	/**
	 * Tiny url not mapped.
	 */
	public final static String NOT_FOUND_DESCRIPTION = "Required tiny not mapped";
	
	/**
	 * Tiny url not mapped.
	 */
	public final static int NOT_VALID_CUSTOM = -4;
	
	/**
	 * Tiny url not mapped.
	 */
	public final static String NOT_VALID_CUSTOM_DESCRIPTION = "Required custom url not valid";
	

	private int returnCode;
	
	private String description;
	
	/**
	 * Default constructor.
	 */
	public Result() {
	}

	/**
	 * Get the return code of processing result.
	 * @return code value or reference.
	 */
	public int getReturnCode() {
		return returnCode;
	}

	/**
	 * Set the return code of processing result.
	 * @param returnCode value or reference.
	 */
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	/**
	 * Get the description associated with the result code.
	 * @return description value or reference.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description associated with the result code.
	 * @param description value or reference.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
