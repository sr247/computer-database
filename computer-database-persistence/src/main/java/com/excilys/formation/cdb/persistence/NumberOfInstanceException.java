package com.excilys.formation.cdb.persistence;

public class NumberOfInstanceException extends DAOException {

	private static final long serialVersionUID = -2979893325048940969L;

	public NumberOfInstanceException() {
		super();
	}

	public NumberOfInstanceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NumberOfInstanceException(String message, Throwable cause) {
		super(message, cause);
	}

	public NumberOfInstanceException(String message) {
		super(message);
	}

	public NumberOfInstanceException(Throwable cause) {
		super(cause);
	}

}
