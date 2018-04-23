package com.excilys.formation.cdb.exceptions;

public class ModifyDatabaseException extends DAOException {

	private static final long serialVersionUID = -7961167233580926877L;

	public ModifyDatabaseException() {
		super();
	}

	public ModifyDatabaseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ModifyDatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModifyDatabaseException(String message) {
		super(message);
	}

	public ModifyDatabaseException(Throwable cause) {
		super(cause);
	}

}
