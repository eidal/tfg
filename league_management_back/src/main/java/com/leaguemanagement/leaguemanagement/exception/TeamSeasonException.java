package com.leaguemanagement.leaguemanagement.exception;

public class TeamSeasonException extends RuntimeException {

	private static final long serialVersionUID = -4182211080687936534L;
	
	public TeamSeasonException(String errorMessage) {
		super(errorMessage);
	}

	public TeamSeasonException(String errorMessage, Throwable error) {
		super(errorMessage, error);
	}
}
