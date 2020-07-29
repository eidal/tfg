package com.leaguemanagement.leaguemanagement.exception;

public class SeasonException extends RuntimeException {

	private static final long serialVersionUID = -4182211080687936534L;
	
	public SeasonException(String errorMessage) {
		super(errorMessage);
	}

	public SeasonException(String errorMessage, Throwable error) {
		super(errorMessage, error);
	}
}
