package com.excilys.formation.cdb.exceptions;

public class InexistentEntityException extends ValidatorException {

	private static final long serialVersionUID = 5070109121984064883L;

	public InexistentEntityException() {
		super();

	}

	public InexistentEntityException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InexistentEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public InexistentEntityException(String message) {
		super(message);
	}

	public InexistentEntityException(Throwable cause) {
		super(cause);
	}

}
