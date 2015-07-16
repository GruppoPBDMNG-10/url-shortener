package it.datatoknowledge.pbdmng.urlShortener.bean;

/**
 * 
 * @author Gianluca Colaianni.
 * The base response class for all response.
 *
 */
public class BaseResponse {
	
	/**
	 * The processing result.
	 */
	private Result result;

	public BaseResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the processing result.
	 * @return result value or reference.
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * Set the processing result.
	 * @param result value or reference.
	 */
	public void setResult(Result result) {
		this.result = result;
	}
	
}
