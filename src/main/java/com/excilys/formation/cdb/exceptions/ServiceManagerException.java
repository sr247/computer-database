package com.excilys.formation.cdb.exceptions;

public class ServiceManagerException extends Exception {

	private static final long serialVersionUID = 12345678910111213L;

	public ServiceManagerException() {
		super();
	}

	public ServiceManagerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceManagerException(String message) {
		super(message);
	}

	public ServiceManagerException(Throwable cause) {
		super(cause);
	}

}
