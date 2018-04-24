package com.excilys.formation.cdb.service.validator;

public class IncorrectFieldException extends ValidatorException {

	private static final long serialVersionUID = 7061341174505257036L;

	public IncorrectFieldException() {
		super();
	}

	public IncorrectFieldException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IncorrectFieldException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectFieldException(String message) {
		super(message);
	}

	public IncorrectFieldException(Throwable cause) {
		super(cause);
	}
	
}
