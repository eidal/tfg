package com.leaguemanagement.leaguemanagement.exception;

public class RefereeException extends RuntimeException {

	private static final long serialVersionUID = -4182211080687936534L;
	
	public RefereeException(String errorMessage) {
		super(errorMessage);
	}

	public RefereeException(String errorMessage, Throwable error) {
		super(errorMessage, error);
	}
}
