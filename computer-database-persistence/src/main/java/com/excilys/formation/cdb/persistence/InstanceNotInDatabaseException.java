package com.excilys.formation.cdb.persistence;

public class InstanceNotInDatabaseException extends DAOException {

	private static final long serialVersionUID = 4984658391421858630L;

	public InstanceNotInDatabaseException() {
		super();
	}

	public InstanceNotInDatabaseException(String message) {
		super(message);
	}

	public InstanceNotInDatabaseException(Throwable cause) {
		super(cause);
	}

	public InstanceNotInDatabaseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InstanceNotInDatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

}
