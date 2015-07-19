package it.datatoknowledge.pbdmng.urlShortener.dao;

/**
 * 
 * @author Gianluca Colaianni
 * Contains the response about a DAO process.
 */
public class DAOResponse {
	
	private DAOResponseCode resultCode;
	
	private Object response;

	/**
	 * Return the process result code.
	 * @return the resultCode
	 */
	public DAOResponseCode getResultCode() {
		return resultCode;
	}

	/**
	 * Set the process result code.
	 * @param resultCode the resultCode to set.
	 */
	public void setResultCode(DAOResponseCode resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * Get the process result object.
	 * @return the response result object.
	 */
	public Object getResponse() {
		return response;
	}

	/**
	 * Set the process result object.
	 * @param response the response to set.
	 */
	public void setResponse(Object response) {
		this.response = response;
	}
	
	

}
