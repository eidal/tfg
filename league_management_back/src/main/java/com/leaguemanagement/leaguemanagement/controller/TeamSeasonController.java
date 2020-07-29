package com.leaguemanagement.leaguemanagement.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leaguemanagement.commons.leaguebase.dto.request.TeamSeasonPlayerRequest;
import com.leaguemanagement.commons.leaguebase.dto.request.TeamSeasonRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.MatchSimpleResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.TeamSeasonPlayerResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.TeamSeasonPlayerSimpleResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.TeamSeasonResponse;
import com.leaguemanagement.leaguemanagement.service.TeamSeasonPlayerService;
import com.leaguemanagement.leaguemanagement.service.TeamSeasonService;
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
public class TeamSeasonController extends MainController {

	private final TeamSeasonService teamSeasonService;
	private final TeamSeasonPlayerService teamSeasonPlayerService;

	public TeamSeasonController(TeamSeasonService teamSeasonService, TeamSeasonPlayerService teamSeasonPlayerService) {
		this.teamSeasonService = teamSeasonService;
		this.teamSeasonPlayerService = teamSeasonPlayerService;
	}

	@ApiOperation(value = "Vinculate a team in a season", response = TeamSeasonResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Team-Season Details Created",response=TeamSeasonResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PostMapping(value = "/team/{idTeam}/season/{idSeason}", produces = "application/json")
	public ResponseEntity<TeamSeasonResponse> createTeamSeason(@ApiParam(value = "Team's identification", required = true)
	@NotNull(message = "Team's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Team's identification should be numeric")
	@PathVariable(value = "idTeam") Long teamId, @ApiParam(value = "Season's identification", required = true)
	@NotNull(message = "Season's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
	@PathVariable(value = "idSeason") Long seasonId, @ApiParam(value = "a team-season object", required = true)
		@Valid @RequestBody TeamSeasonRequest teamSeasonRequest) {
		return new ResponseEntity<>(this.teamSeasonService.createTeamSeason(teamId, seasonId, teamSeasonRequest), HttpStatus.OK);
	}

	@ApiOperation(value = "Get info about a team in a season", response = TeamSeasonResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Team-Season Details Retrieved",response=TeamSeasonResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/team/{idTeam}/season/{idSeason}", produces = "application/json")
	public ResponseEntity<TeamSeasonResponse> getTeamSeason(@ApiParam(value = "Team's identification", required = true)
	@NotNull(message = "Team's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Team's identification should be numeric")
	@PathVariable(value = "idTeam") Long teamId, @ApiParam(value = "Season's identification", required = true)
	@NotNull(message = "Season's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
	@PathVariable(value = "idSeason") Long seasonId) {
		return new ResponseEntity<>(this.teamSeasonService.getTeamSeason(teamId, seasonId), HttpStatus.OK);
	}

	@ApiOperation(value = "Update a team in a season", response = TeamSeasonResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Team-Season Details Updated",response=TeamSeasonResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PutMapping(value = "/team/{idTeam}/season/{idSeason}", produces = "application/json")
	public ResponseEntity<TeamSeasonResponse> createTeamSeason(@ApiParam(value = "Team's identification", required = true)
	@NotNull(message = "Team's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Team's identification should be numeric")
	@PathVariable(value = "idTeam") Long teamId, @ApiParam(value = "Season's identification", required = true)
	@NotNull(message = "Season's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
	@PathVariable(value = "idSeason") Long seasonId, @ApiParam(value = "a team-season parameters", required = true)
		@NotNull @RequestBody Map<String, Object> teamSeasonParameters) {
		return new ResponseEntity<>(this.teamSeasonService.updateTeamSeason(teamId, seasonId, teamSeasonParameters), HttpStatus.OK);
	}

	@ApiOperation(value = "Get a player in a teamSeason by Team and Season", response = TeamSeasonPlayerResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Team-Season-Player Details Retrieved",response=TeamSeasonPlayerResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/team/{idTeam}/season/{idSeason}/player/{idPlayer}", produces = "application/json")
	public ResponseEntity<TeamSeasonPlayerResponse> getTeamSeasonPlayer(@ApiParam(value = "Team's identification", required = true)
	@NotNull(message = "Team's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Team's identification should be numeric")
	@PathVariable(value = "idTeam") Long teamId, @ApiParam(value = "Season's identification", required = true)
	@NotNull(message = "Season's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
	@PathVariable(value = "idSeason") Long seasonId, @ApiParam(value = "Player's identification", required = true)
	@NotNull(message = "Player's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Player's identification should be numeric")
	@PathVariable(value = "idPlayer") Long playerId) {
		return new ResponseEntity<>(this.teamSeasonPlayerService.getTeamSeasonPlayer(teamId, seasonId, playerId),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Get a player in a teamSeason by teamSeasonPlayerId", response = TeamSeasonPlayerResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Team-Season-Player Details Retrieved",response=TeamSeasonPlayerResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/teamSeasonPlayer/{idTeamSeasonPlayer}", produces = "application/json")
	public ResponseEntity<TeamSeasonPlayerResponse> getTeamSeasonPlayerById(@ApiParam(value = "TeamSeasonPlayer's identification", required = true)
	@NotNull(message = "TeamSeasonPlayer's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "TeamSeasonPlayer's identification should be numeric")
	@PathVariable(value = "idTeamSeasonPlayer") Long teamSeasonPlayerId) {
		return new ResponseEntity<>(this.teamSeasonPlayerService.getTeamSeasonPlayerById(teamSeasonPlayerId),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Vinculate a player to a team in a season", response = TeamSeasonPlayerResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Team-Season-Player Details Created",response=TeamSeasonPlayerResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PostMapping(value = "/team/{idTeam}/season/{idSeason}/player/{idPlayer}", produces = "application/json")
	public ResponseEntity<TeamSeasonPlayerResponse> createTeamSeasonPlayer(@ApiParam(value = "Team's identification", required = true)
	@NotNull(message = "Team's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Team's identification should be numeric")
	@PathVariable(value = "idTeam") Long teamId, @ApiParam(value = "Season's identification", required = true)
	@NotNull(message = "Season's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
	@PathVariable(value = "idSeason") Long seasonId, @ApiParam(value = "Player's identification", required = true)
	@NotNull(message = "Player's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Player's identification should be numeric")
	@PathVariable(value = "idPlayer") Long playerId, @ApiParam(value = "a teamseasonplayer request object")
	@Valid @RequestBody TeamSeasonPlayerRequest teamSeasonPlayerRequest) {
		return new ResponseEntity<>(this.teamSeasonPlayerService.createTeamSeasonPlayer(teamId, seasonId, playerId, teamSeasonPlayerRequest),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Update a player in a teamseason", response = TeamSeasonPlayerResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Team-Season-Player Details Updated",response=TeamSeasonPlayerResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PutMapping(value = "/team/{idTeam}/season/{idSeason}/player/{idPlayer}", produces = "application/json")
	public ResponseEntity<TeamSeasonPlayerResponse> updateTeamSeasonPlayer(@ApiParam(value = "Team's identification", required = true)
	@NotNull(message = "Team's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Team's identification should be numeric")
	@PathVariable(value = "idTeam") Long teamId, @ApiParam(value = "Season's identification", required = true)
	@NotNull(message = "Season's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
	@PathVariable(value = "idSeason") Long seasonId, @ApiParam(value = "Player's identification", required = true)
	@NotNull(message = "Player's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Player's identification should be numeric")
	@PathVariable(value = "idPlayer") Long playerId, @ApiParam(value = "a teamseasonplayer request object")
	@RequestBody TeamSeasonPlayerRequest teamSeasonPlayerRequest) {
		return new ResponseEntity<>(this.teamSeasonPlayerService.updateTeamSeasonPlayer(teamId, seasonId, playerId, teamSeasonPlayerRequest),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Update a player in a teamseason by teamSeasonPlayerId", response = TeamSeasonPlayerResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Team-Season-Player Details Updated",response=TeamSeasonPlayerResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PutMapping(value = "/teamSeasonPlayer/{idTeamSeasonPlayer}", produces = "application/json")
	public ResponseEntity<TeamSeasonPlayerResponse> updateTeamSeasonPlayerById(@ApiParam(value = "TeamSeasonPlayer's identification", required = true)
	@NotNull(message = "TeamSeasonPlayer's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "TeamSeasonPlayer's identification should be numeric")
	@PathVariable(value = "idTeamSeasonPlayer") Long teamSeasonPlayerId, @ApiParam(value = "a teamseasonplayer request object")
	@Valid @RequestBody TeamSeasonPlayerRequest teamSeasonPlayerRequest) {
		return new ResponseEntity<>(this.teamSeasonPlayerService.updateTeamSeasonPlayerById(teamSeasonPlayerId, teamSeasonPlayerRequest),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Get list of players in a team-season", response = List.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="List of players in a team-season",response=List.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to View the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Reach is Forbidden"),
    		@ApiResponse(code=404,message="Team-Season Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/team/{idTeam}/season/{idSeason}/player", produces = "application/json")
	public ResponseEntity<List<TeamSeasonPlayerSimpleResponse>> getPlayersInATeamSeason(@ApiParam(value = "Team's identification", required = true)
	@NotNull(message = "Team's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Team's identification should be numeric")
	@PathVariable(value = "idTeam") Long teamId, @ApiParam(value = "Season's identification", required = true)
	@NotNull(message = "Season's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
	@PathVariable(value = "idSeason") Long seasonId) {
		return new ResponseEntity<>(this.teamSeasonPlayerService.getAllTeamSeasonPlayerByTeamSeason(teamId, seasonId),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Get list of home matches in a team-season", response = List.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="List Matches Simple Details Retrieved",response=List.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/team/{idTeam}/season/{idSeason}/matchHome", produces = "application/json")
	public ResponseEntity<List<MatchSimpleResponse>> getAllHomeMatches(@ApiParam(value = "Team's identification", required = true)
	@NotNull(message = "Team's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Team's identification should be numeric")
	@PathVariable(value = "idTeam") Long teamId, @ApiParam(value = "Season's identification", required = true)
	@NotNull(message = "Season's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
	@PathVariable(value = "idSeason") Long seasonId) {
		return new ResponseEntity<>(this.teamSeasonService.getAllHomeMatches(teamId, seasonId), HttpStatus.OK);
	}

	@ApiOperation(value = "Get list of home matches in a team-season with an specific status", response = List.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="List Matches Simple Details Retrieved",response=List.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/team/{idTeam}/season/{idSeason}/matchHome/{matchStatus}", produces = "application/json")
	public ResponseEntity<List<MatchSimpleResponse>> getAllHomeMatchesByStatus(@ApiParam(value = "Team's identification", required = true)
	@NotNull(message = "Team's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Team's identification should be numeric")
	@PathVariable(value = "idTeam") Long teamId, @ApiParam(value = "Season's identification", required = true)
	@NotNull(message = "Season's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
	@PathVariable(value = "idSeason") Long seasonId, @ApiParam(value = "Match's status", required = true)
	@NotEmpty(message = "Match's status is mandatory")
	@PathVariable(value = "matchStatus") String matchStatus) {
		return new ResponseEntity<>(this.teamSeasonService.getAllHomeMatchesByStatus(teamId, seasonId, matchStatus), HttpStatus.OK);
	}

	@ApiOperation(value = "Get list of visitant matches in a team-season", response = List.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="List Matches Simple Details Retrieved",response=List.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/team/{idTeam}/season/{idSeason}/matchVisitant", produces = "application/json")
	public ResponseEntity<List<MatchSimpleResponse>> getAllVisitantMatches(@ApiParam(value = "Team's identification", required = true)
	@NotNull(message = "Team's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Team's identification should be numeric")
	@PathVariable(value = "idTeam") Long teamId, @ApiParam(value = "Season's identification", required = true)
	@NotNull(message = "Season's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
	@PathVariable(value = "idSeason") Long seasonId) {
		return new ResponseEntity<>(this.teamSeasonService.getAllVisitantMatches(teamId, seasonId), HttpStatus.OK);
	}

	@ApiOperation(value = "Get list of visitant matches in a team-season with an specific status", response = List.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="List Matches Simple Details Retrieved",response=List.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/team/{idTeam}/season/{idSeason}/matchVisitant/{matchStatus}", produces = "application/json")
	public ResponseEntity<List<MatchSimpleResponse>> getAllVisitantMatchesByStatus(@ApiParam(value = "Team's identification", required = true)
	@NotNull(message = "Team's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Team's identification should be numeric")
	@PathVariable(value = "idTeam") Long teamId, @ApiParam(value = "Season's identification", required = true)
	@NotNull(message = "Season's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
	@PathVariable(value = "idSeason") Long seasonId, @ApiParam(value = "Match's status", required = true)
	@NotEmpty(message = "Match's status is mandatory")
	@PathVariable(value = "matchStatus") String matchStatus) {
		return new ResponseEntity<>(this.teamSeasonService.getAllVisitantMatchesByStatus(teamId, seasonId, matchStatus), HttpStatus.OK);
	}
}