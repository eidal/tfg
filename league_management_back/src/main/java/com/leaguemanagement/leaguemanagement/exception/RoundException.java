package com.leaguemanagement.leaguemanagement.exception;

public class RoundException extends RuntimeException {

	private static final long serialVersionUID = -4182211080687936534L;
	
	public RoundException(String errorMessage) {
		super(errorMessage);
	}

	public RoundException(String errorMessage, Throwable error) {
		super(errorMessage, error);
	}
}
