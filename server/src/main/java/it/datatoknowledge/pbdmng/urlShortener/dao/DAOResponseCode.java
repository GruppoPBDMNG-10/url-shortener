package it.datatoknowledge.pbdmng.urlShortener.dao;

public enum DAOResponseCode {

	NOT_INSERTED(-1),
	NOT_MAPPED(0),
	INSERTED(1),
	DUPLICATED_KEY(-2),
	UPDATED(2),
	OK(3);
	
	private int response;
	
	private DAOResponseCode(int value) {
		response = value;
	}

	/**
	 * @return the response
	 */
	public int getResponse() {
		return response;
	}
	
	
}
