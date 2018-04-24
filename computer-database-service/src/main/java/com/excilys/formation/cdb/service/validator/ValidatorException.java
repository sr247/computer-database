package com.excilys.formation.cdb.service.validator;

public class ValidatorException extends Exception {

	private static final long serialVersionUID = -4266732324638974667L;

	public ValidatorException() {
		super();
	}

	public ValidatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ValidatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidatorException(String message) {
		super(message);
	}

	public ValidatorException(Throwable cause) {
		super(cause);
	}

}
