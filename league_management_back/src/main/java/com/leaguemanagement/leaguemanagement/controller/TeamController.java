package com.leaguemanagement.leaguemanagement.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

import com.leaguemanagement.commons.leaguebase.dto.request.TeamRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.TeamResponse;
import com.leaguemanagement.leaguemanagement.service.TeamService;
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
public class TeamController extends MainController {

	private final TeamService teamService;

	public TeamController(TeamService teamService) {
		this.teamService = teamService;
	}

	@ApiOperation(value = "Get list teams", response = List.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Teams Details Retrieved",response=List.class),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/team", produces = "application/json")
	public ResponseEntity<List<TeamResponse>> getAllTeams() {
		return new ResponseEntity<>(this.teamService.getAllTeams(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get team by Id", response = TeamResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Team Details Retrieved",response=TeamResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to View the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Reach is Forbidden"),
    		@ApiResponse(code=404,message="Team Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/team/{id}", produces = "application/json")
	public ResponseEntity<TeamResponse> getTeamById(@ApiParam(value = "the team's identification", required = true)
	@NotNull(message = "Team's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Team identification should be numeric")
	@PathVariable(value = "id") Long teamId) {
		return new ResponseEntity<>(this.teamService.getTeamById(teamId), HttpStatus.OK);
	}

	@ApiOperation(value = "Get list of teams by Name", response = TeamResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Team Details Retrieved",response=TeamResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to View the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Reach is Forbidden"),
    		@ApiResponse(code=404,message="Team Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/team/name/{name}", produces = "application/json")
	public ResponseEntity<List<TeamResponse>> getTeamByName(@ApiParam(value = "the team's name", required = true)
	@NotEmpty(message = "Team's name is mandatory")
	@Size(max = 50, message = "Team's name should not be greater than 50 characters")
	@PathVariable(value = "name") String teamName) {
		return new ResponseEntity<>(this.teamService.getTeamByName(teamName), HttpStatus.OK);
	}

	@ApiOperation(value = "Create team", response = TeamResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Team Details Created",response=TeamResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PostMapping(value = "/team", produces = "application/json")
	public ResponseEntity<TeamResponse> createTeam(@ApiParam(value = "a team object", required = true)
		@Valid @RequestBody TeamRequest teamRequest) {
		return new ResponseEntity<>(this.teamService.createTeam(teamRequest), HttpStatus.OK);
	}

	@ApiOperation(value = "Update team by Id", response = TeamResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Team Details Updated",response=TeamResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to Update the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Update is Forbidden"),
    		@ApiResponse(code=404,message="Team Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PutMapping(value = "/team/{id}", produces = "application/json")
	public ResponseEntity<TeamResponse> updateTeamById(@ApiParam(value = "the team's identification", required = true)
	@NotNull(message = "Team's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Team identification should be numeric")
	@PathVariable(value = "id") Long teamId, @ApiParam(value = "a team object", required = true)
		@NotNull @RequestBody Map<String,Object> teamParameters) {
		return new ResponseEntity<>(this.teamService.updateTeamById(teamId, teamParameters), HttpStatus.OK);
	}

	@ApiOperation(value = "Delete team by Id", response = String.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Team Details Deleted",response=String.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to Delete the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Delete is Forbidden"),
    		@ApiResponse(code=404,message="Team Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@DeleteMapping(value = "/team/{id}", produces = "application/json")
	public ResponseEntity<String> deleteTeamById(@ApiParam(value = "the team's identification", required = true)
	@NotNull(message = "Team's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Team identification should be numeric")
	@PathVariable(value = "id") Long teamId) {
		return new ResponseEntity<>(this.teamService.deleteTeamById(teamId), HttpStatus.OK);
	}
}
