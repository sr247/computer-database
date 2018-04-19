package com.excilys.formation.cdb.exceptions;

public class CloseConnectionException extends DAOException {

	private static final long serialVersionUID = -7064873078805757970L;
	
	public CloseConnectionException() {
		super();
	}

	public CloseConnectionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CloseConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public CloseConnectionException(String message) {
		super(message);
	}

	public CloseConnectionException(Throwable cause) {
		super(cause);
	}

}
