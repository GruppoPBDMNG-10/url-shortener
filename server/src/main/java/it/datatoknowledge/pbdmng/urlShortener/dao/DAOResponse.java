package it.datatoknowledge.pbdmng.urlShortener.dao;

public class DAOResponse {
	
	private DAOResponseCode resultCode;
	
	private Object response;

	public DAOResponse() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the resultCode
	 */
	public DAOResponseCode getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(DAOResponseCode resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return the response
	 */
	public Object getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(Object response) {
		this.response = response;
	}
	
	

}
