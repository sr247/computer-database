package com.excilys.formation.cdb.exceptions;

public class NoNameProvidedException extends ValidatorException {

	private static final long serialVersionUID = -5102850543650694510L;

	public NoNameProvidedException() {
		super();
	}

	public NoNameProvidedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoNameProvidedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoNameProvidedException(String message) {
		super(message);
	}

	public NoNameProvidedException(Throwable cause) {
		super(cause);
	}



}
