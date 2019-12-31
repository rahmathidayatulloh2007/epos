package com.wo.epos.common.exception;

public class ExceptionResourceBundled extends Exception {

	private static final long serialVersionUID = 2033163168539589810L;

	public ExceptionResourceBundled() {
	}

	public ExceptionResourceBundled(String message) {
		super(message);
	}

	public ExceptionResourceBundled(Throwable cause) {
		super(cause);
	}

	public ExceptionResourceBundled(String message, Throwable cause) {
		super(message, cause);
	}

}
