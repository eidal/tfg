package com.leaguemanagement.leaguemanagement.exception;

public class MatchException extends RuntimeException {

	private static final long serialVersionUID = -4182211080687936534L;
	
	public MatchException(String errorMessage) {
		super(errorMessage);
	}

	public MatchException(String errorMessage, Throwable error) {
		super(errorMessage, error);
	}
}
