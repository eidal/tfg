package com.leaguemanagement.leaguemanagement.exception;

public class CompetitionSeasonException extends RuntimeException {

	private static final long serialVersionUID = -4182211080687936534L;
	
	public CompetitionSeasonException(String errorMessage) {
		super(errorMessage);
	}

	public CompetitionSeasonException(String errorMessage, Throwable error) {
		super(errorMessage, error);
	}
}
