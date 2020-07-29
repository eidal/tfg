package com.leaguemanagement.leaguemanagement.controller;

import java.util.List;
import java.util.Set;

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

import com.leaguemanagement.commons.leaguebase.dto.request.CompetitionSeasonRequest;
import com.leaguemanagement.commons.leaguebase.dto.request.RoundRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.CompetitionSeasonRefereeResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.CompetitionSeasonResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.MatchSimpleResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.RefereeSimpleResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.RoundResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.RoundSimpleResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.TeamSeasonSimpleResponse;
import com.leaguemanagement.leaguemanagement.service.CompetitionSeasonService;
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
public class CompetitionSeasonController extends MainController {

	private final CompetitionSeasonService competitionSeasonService;

	public CompetitionSeasonController(CompetitionSeasonService competitionSeasonService) {
		this.competitionSeasonService = competitionSeasonService;
	}

	@ApiOperation(value = "Vinculate a season to a competition", response = CompetitionSeasonResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Competition-Season Details Created", response = CompetitionSeasonResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping(value = "/competition/{idCompetition}/season/{idSeason}", produces = "application/json")
	public ResponseEntity<CompetitionSeasonResponse> createCompetitionSeason(
			@ApiParam(value = "Competition's identification", required = true)
			@NotNull(message = "Competition's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Competition's identification should be numeric")
			@PathVariable(value = "idCompetition") Long competitionId,
			@ApiParam(value = "Season's identification", required = true)
			@NotNull(message = "Season's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
			@PathVariable(value = "idSeason") Long seasonId,
			@ApiParam(value = "a competition-season object", required = true)
			@Valid @RequestBody CompetitionSeasonRequest competitionSeasonRequest) {
		return new ResponseEntity<>(this.competitionSeasonService.createCompetitionSeason(competitionId, seasonId,
				competitionSeasonRequest), HttpStatus.OK);
	}

	@ApiOperation(value = "Get competition-season detail by Ids", response = CompetitionSeasonResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Competition-Season Details Retrieved", response = CompetitionSeasonResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "You Are Not Authorized to View the Resource"),
			@ApiResponse(code = 403, message = "Accessing the Resource you Were Trying to Reach is Forbidden"),
			@ApiResponse(code = 404, message = "Competition-Season Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/competition/{idCompetition}/season/{idSeason}", produces = "application/json")
	public ResponseEntity<CompetitionSeasonResponse> getCompetitionById(
			@ApiParam(value = "Competition's identification", required = true)
			@NotNull(message = "Competition's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Competition's identification should be numeric")
			@PathVariable(value = "idCompetition") Long competitionId,
			@ApiParam(value = "Season's identification", required = true)
			@NotNull(message = "Season's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
			@PathVariable(value = "idSeason") Long seasonId) {
		return new ResponseEntity<>(this.competitionSeasonService.getCompetitionSeason(competitionId, seasonId),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Vinculate a referee to a competition in a season", response = CompetitionSeasonRefereeResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Competition-Season-Referee Details Created", response = String.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping(value = "/competition/{idCompetition}/season/{idSeason}/referee/{idReferee}", produces = "application/json")
	public ResponseEntity<String> createCompetitionSeasonReferee(
			@ApiParam(value = "Competition's identification", required = true)
			@NotNull(message = "Competition's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Competition's identification should be numeric")
			@PathVariable(value = "idCompetition") Long competitionId,
			@ApiParam(value = "Season's identification", required = true)
			@NotNull(message = "Season's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
			@PathVariable(value = "idSeason") Long seasonId,
			@ApiParam(value = "Referee's identification", required = true)
			@NotNull(message = "Referee's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Referee's identification should be numeric")
			@PathVariable(value = "idReferee") Long refereeId) {
		return new ResponseEntity<>(
				this.competitionSeasonService.vinculateRefereeToCompetitionSeason(competitionId, seasonId, refereeId),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Get list of referees in a competition-season", response = Set.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of referees in a competition-season", response = Set.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "You Are Not Authorized to View the Resource"),
			@ApiResponse(code = 403, message = "Accessing the Resource you Were Trying to Reach is Forbidden"),
			@ApiResponse(code = 404, message = "Competition-Season Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/competition/{idCompetition}/season/{idSeason}/referee", produces = "application/json")
	public ResponseEntity<List<RefereeSimpleResponse>> getRefereesInACompetitionSeason(
			@ApiParam(value = "Competition's identification", required = true)
			@NotNull(message = "Competition's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Competition's identification should be numeric")
			@PathVariable(value = "idCompetition") Long competitionId,
			@ApiParam(value = "Season's identification", required = true)
			@NotNull(message = "Season's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
			@PathVariable(value = "idSeason") Long seasonId) {
		return new ResponseEntity<>(this.competitionSeasonService.getAllReferees(competitionId, seasonId),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Vinculate a team to a competition in a season", response = CompetitionSeasonRefereeResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Competition-Season-Team Details Created", response = String.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping(value = "/competition/{idCompetition}/season/{idSeason}/team/{idTeam}", produces = "application/json")
	public ResponseEntity<String> createCompetitionSeasonTeamSeason(
			@ApiParam(value = "Competition's identification", required = true)
			@NotNull(message = "Competition's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Competition's identification should be numeric")
			@PathVariable(value = "idCompetition") Long competitionId,
			@ApiParam(value = "Season's identification", required = true)
			@NotNull(message = "Season's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
			@PathVariable(value = "idSeason") Long seasonId,
			@ApiParam(value = "Team's identification", required = true)
			@NotNull(message = "Team's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Team's identification should be numeric")
			@PathVariable(value = "idTeam") Long teamId) {
		return new ResponseEntity<>(
				this.competitionSeasonService.vinculateTeamToCompetitionSeason(competitionId, seasonId, teamId),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Get list of teams in a competition-season", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of teams in a competition-season", response = List.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "You Are Not Authorized to View the Resource"),
			@ApiResponse(code = 403, message = "Accessing the Resource you Were Trying to Reach is Forbidden"),
			@ApiResponse(code = 404, message = "Competition-Season Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/competition/{idCompetition}/season/{idSeason}/team", produces = "application/json")
	public ResponseEntity<List<TeamSeasonSimpleResponse>> getTeamsInACompetitionSeason(
			@ApiParam(value = "Competition's identification", required = true)
			@NotNull(message = "Competition's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Competition's identification should be numeric")
			@PathVariable(value = "idCompetition") Long competitionId,
			@ApiParam(value = "Season's identification", required = true)
			@NotNull(message = "Season's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
			@PathVariable(value = "idSeason") Long seasonId) {
		return new ResponseEntity<>(this.competitionSeasonService.getAllTeamSeasons(competitionId, seasonId),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Get list of rounds in a competition-season", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of rounds in a competition-season", response = List.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "You Are Not Authorized to View the Resource"),
			@ApiResponse(code = 403, message = "Accessing the Resource you Were Trying to Reach is Forbidden"),
			@ApiResponse(code = 404, message = "Competition-Season Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/competition/{idCompetition}/season/{idSeason}/round", produces = "application/json")
	public ResponseEntity<List<RoundSimpleResponse>> getRoundsInACompetitionSeason(
			@ApiParam(value = "Competition's identification", required = true)
			@NotNull(message = "Competition's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Competition's identification should be numeric")
			@PathVariable(value = "idCompetition") Long competitionId,
			@ApiParam(value = "Season's identification", required = true)
			@NotNull(message = "Season's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
			@PathVariable(value = "idSeason") Long seasonId) {
		return new ResponseEntity<>(this.competitionSeasonService.getAllRounds(competitionId, seasonId),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Get a round in a competition-season", response = RoundResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "A round in a competition-season", response = RoundResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "You Are Not Authorized to View the Resource"),
			@ApiResponse(code = 403, message = "Accessing the Resource you Were Trying to Reach is Forbidden"),
			@ApiResponse(code = 404, message = "Competition-Season Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/competition/{idCompetition}/season/{idSeason}/round/{roundNumber}", produces = "application/json")
	public ResponseEntity<RoundResponse> getRoundInACompetitionSeason(
			@ApiParam(value = "Competition's identification", required = true)
			@NotNull(message = "Competition's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Competition's identification should be numeric")
			@PathVariable(value = "idCompetition") Long competitionId,
			@ApiParam(value = "Season's identification", required = true)
			@NotNull(message = "Season's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
			@PathVariable(value = "idSeason") Long seasonId,
			@ApiParam(value = "Round's number", required = true)
			@NotNull(message = "Round's number is mandatory")
			@Digits(integer=10, fraction=0, message = "Round's number should be numeric")
			@PathVariable(value = "roundNumber") Integer roundNumber) {
		return new ResponseEntity<>(this.competitionSeasonService.getRoundInACompetitionSeason(competitionId, seasonId, roundNumber),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Update a round in a competition-season", response = RoundResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "A round in a competition-season", response = RoundResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "You Are Not Authorized to View the Resource"),
			@ApiResponse(code = 403, message = "Accessing the Resource you Were Trying to Reach is Forbidden"),
			@ApiResponse(code = 404, message = "Competition-Season Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PutMapping(value = "/competition/{idCompetition}/season/{idSeason}/round/{roundNumber}", produces = "application/json")
	public ResponseEntity<RoundResponse> updateRoundInACompetitionSeason(
			@ApiParam(value = "Competition's identification", required = true)
			@NotNull(message = "Competition's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Competition's identification should be numeric")
			@PathVariable(value = "idCompetition") Long competitionId,
			@ApiParam(value = "Season's identification", required = true)
			@NotNull(message = "Season's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
			@PathVariable(value = "idSeason") Long seasonId,
			@ApiParam(value = "Round's number", required = true)
			@NotNull(message = "Round's number is mandatory")
			@Digits(integer=10, fraction=0, message = "Round's number should be numeric")
			@PathVariable(value = "roundNumber") Integer roundNumber,
			@ApiParam(value = "Round request object", required = true)
			@NotNull(message = "Round request is mandatory")
			@RequestBody RoundRequest roundRequest) {
		return new ResponseEntity<>(this.competitionSeasonService.updateRoundInACompetitionSeason(competitionId, seasonId,
				roundNumber, roundRequest),	HttpStatus.OK);
	}

	@ApiOperation(value = "Get list of matches in a round in a competition-season", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "A list of matches in a round in a competition-season", response = List.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "You Are Not Authorized to View the Resource"),
			@ApiResponse(code = 403, message = "Accessing the Resource you Were Trying to Reach is Forbidden"),
			@ApiResponse(code = 404, message = "Competition-Season Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/competition/{idCompetition}/season/{idSeason}/round/{roundNumber}/matches", produces = "application/json")
	public ResponseEntity<List<MatchSimpleResponse>> getMatchesRoundInACompetitionSeason(
			@ApiParam(value = "Competition's identification", required = true)
			@NotNull(message = "Competition's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Competition's identification should be numeric")
			@PathVariable(value = "idCompetition") Long competitionId,
			@ApiParam(value = "Season's identification", required = true)
			@NotNull(message = "Season's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
			@PathVariable(value = "idSeason") Long seasonId,
			@ApiParam(value = "Round's number", required = true)
			@NotNull(message = "Round's number is mandatory")
			@Digits(integer=10, fraction=0, message = "Round's number should be numeric")
			@PathVariable(value = "roundNumber") Integer roundNumber) {
		return new ResponseEntity<>(this.competitionSeasonService.getMatchesRoundInACompetitionSeason(competitionId, seasonId, roundNumber),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Get list of matches with specific status in a round in a competition-season", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "A list of matches in a round in a competition-season", response = List.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "You Are Not Authorized to View the Resource"),
			@ApiResponse(code = 403, message = "Accessing the Resource you Were Trying to Reach is Forbidden"),
			@ApiResponse(code = 404, message = "Competition-Season Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/competition/{idCompetition}/season/{idSeason}/round/{roundNumber}/matches/status/{matchStatus}", produces = "application/json")
	public ResponseEntity<List<MatchSimpleResponse>> getMatchesRoundInACompetitionSeasonWithStatus(
			@ApiParam(value = "Competition's identification", required = true)
			@NotNull(message = "Competition's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Competition's identification should be numeric")
			@PathVariable(value = "idCompetition") Long competitionId,
			@ApiParam(value = "Season's identification", required = true)
			@NotNull(message = "Season's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
			@PathVariable(value = "idSeason") Long seasonId,
			@ApiParam(value = "Round's number", required = true)
			@NotNull(message = "Round's number is mandatory")
			@Digits(integer=10, fraction=0, message = "Round's number should be numeric")
			@PathVariable(value = "roundNumber") Integer roundNumber,
			@ApiParam(value = "Match's status", required = true)
			@NotEmpty(message = "Match's status is mandatory")
			@PathVariable(value = "matchStatus") String matchStatus) {
		return new ResponseEntity<>(this.competitionSeasonService.getMatchesRoundInACompetitionSeasonWithStatus(competitionId, seasonId,
				roundNumber, matchStatus), HttpStatus.OK);
	}

	@ApiOperation(value = "Generate competition-season's matches automatically for rounds")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "Competition-Season matches generate correctly"),
					@ApiResponse(code = 400, message = "Bad Request"),
					@ApiResponse(code = 401, message = "You Are Not Authorized to View the Resource"),
					@ApiResponse(code = 403, message = "Accessing the Resource you Were Trying to Reach is Forbidden"),
					@ApiResponse(code = 404, message = "Competition-Season Not Found"),
					@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/competition/{idCompetition}/season/{idSeason}/matches/generate", produces = "application/json")
	public ResponseEntity<Void> generateCompetitionSeasonMatches(
					@ApiParam(value = "Competition's identification", required = true)
					@NotNull(message = "Competition's identification is mandatory")
					@Digits(integer=10, fraction=0, message = "Competition's identification should be numeric")
					@PathVariable(value = "idCompetition") Long competitionId,
					@ApiParam(value = "Season's identification", required = true)
					@NotNull(message = "Season's identification is mandatory")
					@Digits(integer=10, fraction=0, message = "Season's identification should be numeric")
					@PathVariable(value = "idSeason") Long seasonId) {
		this.competitionSeasonService.generateMatchesAutomatically(competitionId, seasonId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
