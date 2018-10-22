package com.googosoft.util.ireport;

public class IreportException extends Exception {

	private static final long serialVersionUID = -8007658564262046002L;

	public IreportException() {
		super();
	}

	public IreportException(String string) {
		super(string);
	}

	public IreportException(Throwable throwable) {
		super(throwable);
	}

	public IreportException(String string, Throwable throwable) {
		super(string, throwable);
	}
}
