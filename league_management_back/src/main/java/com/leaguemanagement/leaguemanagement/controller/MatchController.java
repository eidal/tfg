package com.leaguemanagement.leaguemanagement.controller;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leaguemanagement.commons.leaguebase.dto.request.MatchRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.MatchResponse;
import com.leaguemanagement.leaguemanagement.service.MatchService;
import com.leaguemanagement.leaguemanagement.utils.LeagueManagementConsts;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(LeagueManagementConsts.MANAGEMENT_V1)
@Api(value = "League Management API REST")
@Slf4j
public class MatchController extends MainController {

	private final MatchService matchService;

	public MatchController(final MatchService matchService) {
		this.matchService = matchService;
	}
	@ApiOperation(value = "Get match by Id", response = MatchResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Match Details Retrieved",response=MatchResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to View the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Reach is Forbidden"),
    		@ApiResponse(code=404,message="Match Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/match/{id}", produces = "application/json")
	public ResponseEntity<MatchResponse> getMatchById(@ApiParam(value = "the match's identification", required = true)
	@NotNull(message = "Match's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Match identification should be numeric")
		@PathVariable(value = "id") Long matchId) {
		return new ResponseEntity<>(this.matchService.getMatchById(matchId), HttpStatus.OK);
	}

	@ApiOperation(value = "Create match", response = MatchResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Match Details Created",response=MatchResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PostMapping(value = "/match", produces = "application/json")
	public ResponseEntity<MatchResponse> createMatch(@ApiParam(value = "a match object", required = true)
		@Valid @RequestBody MatchRequest matchRequest) {
		return new ResponseEntity<>(this.matchService.createMatch(matchRequest), HttpStatus.OK);
	}

	@ApiOperation(value = "Update match by Id", response = MatchResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Match Details Updated",response=MatchResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to Update the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Update is Forbidden"),
    		@ApiResponse(code=404,message="Match Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PutMapping(value = "/match/{id}", produces = "application/json")
	public ResponseEntity<MatchResponse> updateMatchById(@ApiParam(value = "the match's identification", required = true)
	@NotNull(message = "Match's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Match identification should be numeric")
	@PathVariable(value = "id") Long matchId, @ApiParam(value = "a match object", required = true)
		@Valid @RequestBody MatchRequest matchRequest) {
		return new ResponseEntity<>(this.matchService.updateMatchById(matchId, matchRequest), HttpStatus.OK);
	}

	@ApiOperation(value = "Delete match by Id", response = String.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Match Details Deleted",response=String.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to Delete the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Delete is Forbidden"),
    		@ApiResponse(code=404,message="Match Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@DeleteMapping(value = "/match/{id}", produces = "application/json")
	public ResponseEntity<String> deleteMatchById(@ApiParam(value = "the match's identification", required = true)
	@NotNull(message = "Match's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Match identification should be numeric")
	@PathVariable(value = "id") Long matchId) {
		return new ResponseEntity<>(this.matchService.deleteMatchById(matchId), HttpStatus.OK);
	}
}
