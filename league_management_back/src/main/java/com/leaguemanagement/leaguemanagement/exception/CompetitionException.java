package com.leaguemanagement.leaguemanagement.exception;

public class CompetitionException extends RuntimeException {

	private static final long serialVersionUID = -4182211080687936534L;
	
	public CompetitionException(String errorMessage) {
		super(errorMessage);
	}

	public CompetitionException(String errorMessage, Throwable error) {
		super(errorMessage, error);
	}
}
