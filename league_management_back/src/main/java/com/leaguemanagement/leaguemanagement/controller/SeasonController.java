package com.leaguemanagement.leaguemanagement.controller;

import java.util.List;
import java.util.Map;

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

import com.leaguemanagement.commons.leaguebase.dto.request.SeasonRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.SeasonResponse;
import com.leaguemanagement.leaguemanagement.service.SeasonService;
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
public class SeasonController extends MainController {

	private final SeasonService seasonService;


	public SeasonController(SeasonService seasonService) {
		this.seasonService = seasonService;
	}

	@ApiOperation(value = "Get list seasons", response = List.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Seasons Details Retrieved",response=List.class),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/season", produces = "application/json")
	public ResponseEntity<List<SeasonResponse>> getAllSeasons() {
		return new ResponseEntity<>(this.seasonService.getAllSeasons(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get season by Id", response = SeasonResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Season Details Retrieved",response=SeasonResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to View the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Reach is Forbidden"),
    		@ApiResponse(code=404,message="Season Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/season/{id}", produces = "application/json")
	public ResponseEntity<SeasonResponse> getSeasonById(@ApiParam(value = "the season's identification", required = true)
	@NotNull(message = "Season identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Season identification should be numeric")
	@PathVariable(value = "id") Long seasonId) {
		return new ResponseEntity<>(this.seasonService.getSeasonById(seasonId), HttpStatus.OK);
	}

	@ApiOperation(value = "Get season by year start", response = SeasonResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Season Details Retrieved",response=SeasonResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to View the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Reach is Forbidden"),
    		@ApiResponse(code=404,message="Season Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/season/year/start/{year}", produces = "application/json")
	public ResponseEntity<SeasonResponse> getSeasonByYearStart(@ApiParam(value = "the season's year start", required = true)
	@NotNull(message = "Season year start is mandatory")
	@Digits(integer=4, fraction=0, message = "Season year start should be numeric")
	@PathVariable(value = "year") Integer yearStart) {
		return new ResponseEntity<>(this.seasonService.getSeasonByYearStart(yearStart), HttpStatus.OK);
	}

	@ApiOperation(value = "Create season", response = SeasonResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Season Details Created",response=SeasonResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PostMapping(value = "/season", produces = "application/json")
	public ResponseEntity<SeasonResponse> createSeason(@ApiParam(value = "a season object", required = true)
		@Valid @RequestBody SeasonRequest seasonRequest) {
		return new ResponseEntity<>(this.seasonService.createSeason(seasonRequest), HttpStatus.OK);
	}

	@ApiOperation(value = "Update season by Id", response = SeasonResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Season Details Updated",response=SeasonResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to Update the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Update is Forbidden"),
    		@ApiResponse(code=404,message="Season Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PutMapping(value = "/season/{id}", produces = "application/json")
	public ResponseEntity<SeasonResponse> updateSeasonById(@ApiParam(value = "the season's identification", required = true)
	@NotNull(message = "Season identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Season identification should be numeric")
	@PathVariable(value = "id") Long seasonId, @ApiParam(value = "a season object", required = true)
		@NotNull @RequestBody Map<String, Object> seasonParameters) {
		return new ResponseEntity<>(this.seasonService.updateSeasonById(seasonId, seasonParameters), HttpStatus.OK);
	}

	@ApiOperation(value = "Delete season by Id", response = String.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Season Details Deleted",response=String.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to Delete the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Delete is Forbidden"),
    		@ApiResponse(code=404,message="Season Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@DeleteMapping(value = "/season/{id}", produces = "application/json")
	public ResponseEntity<String> deleteSeasonById(@ApiParam(value = "the season's identification", required = true)
	@NotNull(message = "Season identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Season identification should be numeric")
	@PathVariable(value = "id") Long seasonId) {
		return new ResponseEntity<>(this.seasonService.deleteSeasonById(seasonId), HttpStatus.OK);
	}
}