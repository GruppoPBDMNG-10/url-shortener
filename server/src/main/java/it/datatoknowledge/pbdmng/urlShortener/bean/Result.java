package it.datatoknowledge.pbdmng.urlShortener.bean;

public class Result {
	
	public final static int OK_RETURN_CODE = 0;
	
	public final static String OK_DESCRIPTION = "Processed completed";
	
	public final static int  GENERIC_ERROR_RETURN_CODE = -1;
	
	public final static String GENERIC_ERROR_DESCRIPTION = "An error as occourred";
	

	private int returnCode;
	
	private String description;
	
	public Result() {
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
