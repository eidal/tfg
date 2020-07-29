package com.leaguemanagement.leaguemanagement.exception;

public class PlayerException extends RuntimeException {

	private static final long serialVersionUID = -4182211080687936534L;
	
	public PlayerException(String errorMessage) {
		super(errorMessage);
	}

	public PlayerException(String errorMessage, Throwable error) {
		super(errorMessage, error);
	}
}
