package com.leaguemanagement.leaguemanagement.exception;

public class TeamException extends RuntimeException {

	private static final long serialVersionUID = -4182211080687936534L;
	
	public TeamException(String errorMessage) {
		super(errorMessage);
	}

	public TeamException(String errorMessage, Throwable error) {
		super(errorMessage, error);
	}
}
