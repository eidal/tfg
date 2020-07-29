package com.leaguemanagement.leaguemanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.leaguemanagement.leaguemanagement.exception.CompetitionException;
import com.leaguemanagement.leaguemanagement.exception.CompetitionSeasonException;
import com.leaguemanagement.leaguemanagement.exception.MatchException;
import com.leaguemanagement.leaguemanagement.exception.PlayerException;
import com.leaguemanagement.leaguemanagement.exception.RefereeException;
import com.leaguemanagement.leaguemanagement.exception.RoundException;
import com.leaguemanagement.leaguemanagement.exception.SeasonException;
import com.leaguemanagement.leaguemanagement.exception.TeamException;
import com.leaguemanagement.leaguemanagement.exception.TeamSeasonException;
import com.leaguemanagement.leaguemanagement.exception.TeamSeasonPlayerException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({CompetitionException.class, CompetitionSeasonException.class,
		MatchException.class, PlayerException.class, RefereeException.class, RoundException.class,
		SeasonException.class, TeamException.class, TeamSeasonException.class, 
		TeamSeasonPlayerException.class})
	public final ResponseEntity<String> handleNotFoundException(Exception exception, WebRequest request) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({MethodArgumentTypeMismatchException.class, MethodArgumentNotValidException.class,
		HttpMessageNotReadableException.class})
	public final ResponseEntity<String> handleMethodArgumentException(Exception exception, WebRequest request) {
		return new ResponseEntity<>("Bad Arguments", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<String> handleGeneralException(Exception exception, WebRequest request) {
		return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
