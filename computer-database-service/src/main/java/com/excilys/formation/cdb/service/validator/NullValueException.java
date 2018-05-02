package com.excilys.formation.cdb.service.validator;

public class NullValueException extends ValidatorException {

	private static final long serialVersionUID = -2418309645770386367L;

	public NullValueException() {
		super();
	}

	public NullValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NullValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullValueException(String message) {
		super(message);
	}

	public NullValueException(Throwable cause) {
		super(cause);
	}

}
