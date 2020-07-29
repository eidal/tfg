package com.leaguemanagement.leaguemanagement.exception;

public class TeamSeasonPlayerException extends RuntimeException {

	private static final long serialVersionUID = -4182211080687936534L;
	
	public TeamSeasonPlayerException(String errorMessage) {
		super(errorMessage);
	}

	public TeamSeasonPlayerException(String errorMessage, Throwable error) {
		super(errorMessage, error);
	}
}
