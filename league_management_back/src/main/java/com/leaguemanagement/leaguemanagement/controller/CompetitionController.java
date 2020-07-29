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

import com.leaguemanagement.commons.leaguebase.dto.request.CompetitionRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.CompetitionResponse;
import com.leaguemanagement.leaguemanagement.service.CompetitionService;
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
public class CompetitionController extends MainController {

	private final CompetitionService competitionService;

	public CompetitionController(CompetitionService competitionService) {
		this.competitionService = competitionService;
	}

	@ApiOperation(value = "Get list competitions", response = List.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Competitions Details Retrieved",response=List.class),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/competition", produces = "application/json")
	public ResponseEntity<List<CompetitionResponse>> getAllCompetitions() {
		return new ResponseEntity<>(this.competitionService.getAllCompetitions(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get competition by Id", response = CompetitionResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Competition Details Retrieved",response=CompetitionResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to View the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Reach is Forbidden"),
    		@ApiResponse(code=404,message="Competition Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/competition/{id}", produces = "application/json")
	public ResponseEntity<CompetitionResponse> getCompetitionById(@ApiParam(value = "the competition's identification", required = true)
	@NotNull(message = "Competition's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Competition identification should be numeric")
	@PathVariable(value = "id") Long competitionId) {
		return new ResponseEntity<>(this.competitionService.getCompetitionById(competitionId), HttpStatus.OK);
	}

	@ApiOperation(value = "Get competitions by name", response = CompetitionResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Competition Details Retrieved",response=CompetitionResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to View the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Reach is Forbidden"),
    		@ApiResponse(code=404,message="Competition Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/competition/name/{name}", produces = "application/json")
	public ResponseEntity<List<CompetitionResponse>> getCompetitionByName(@ApiParam(value = "the competition's name", required = true)
	@NotEmpty(message = "Competition's name is mandatory")
	@Size(max = 50, message = "Competition's name should not be greater than 50 characters")
	@PathVariable(value = "name") String competitionName) {
		return new ResponseEntity<>(this.competitionService.getCompetitionByName(competitionName), HttpStatus.OK);
	}


	@ApiOperation(value = "Create competition", response = CompetitionResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Competition Details Created",response=CompetitionResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PostMapping(value = "/competition", produces = "application/json")
	public ResponseEntity<CompetitionResponse> createCompetition(@ApiParam(value = "a competition object", required = true)
		@Valid @RequestBody CompetitionRequest competitionRequest) {
		return new ResponseEntity<>(this.competitionService.createCompetition(competitionRequest), HttpStatus.OK);
	}

	@ApiOperation(value = "Update competition by Id", response = CompetitionResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Competition Details Updated",response=CompetitionResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to Update the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Update is Forbidden"),
    		@ApiResponse(code=404,message="Competition Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PutMapping(value = "/competition/{id}", produces = "application/json")
	public ResponseEntity<CompetitionResponse> updateCompetitionById(@ApiParam(value = "the competition's identification", required = true)
	@NotNull(message = "Competition's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Competition identification should be numeric")
	@PathVariable(value = "id") Long competitionId, @ApiParam(value = "a competition object", required = true)
	@NotNull @RequestBody Map<String, Object> competitionParameters) {
		return new ResponseEntity<>(this.competitionService.updateCompetitionById(competitionId, competitionParameters),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Delete competition by Id", response = String.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Competition Details Deleted",response=String.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to Delete the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Delete is Forbidden"),
    		@ApiResponse(code=404,message="Competition Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@DeleteMapping(value = "/competition/{id}", produces = "application/json")
	public ResponseEntity<String> deleteCompetitionById(@ApiParam(value = "the competition's identification", required = true)
	@NotNull(message = "Competition's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Competition identification should be numeric")
	@PathVariable(value = "id") Long competitionId) {
		return new ResponseEntity<>(this.competitionService.deleteCompetitionById(competitionId), HttpStatus.OK);
	}
}
