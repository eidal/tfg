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

import com.leaguemanagement.commons.leaguebase.dto.request.RefereeRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.MatchSimpleResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.RefereeResponse;
import com.leaguemanagement.leaguemanagement.service.RefereeService;
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
public class RefereeController extends MainController {

	private final RefereeService refereeService;

	public RefereeController(RefereeService refereeService) {
		this.refereeService = refereeService;
	}

	@ApiOperation(value = "Get list referees", response = List.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Referees Details Retrieved",response=List.class),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/referee", produces = "application/json")
	public ResponseEntity<List<RefereeResponse>> getAllReferees() {
		return new ResponseEntity<>(this.refereeService.getAllReferees(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get referee by Id", response = RefereeResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Referee Details Retrieved",response=RefereeResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to View the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Reach is Forbidden"),
    		@ApiResponse(code=404,message="Referee Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/referee/{id}", produces = "application/json")
	public ResponseEntity<RefereeResponse> getRefereeById(@ApiParam(value = "the referee's identification", required = true)
	@NotNull(message = "Referee's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Referee identification should be numeric")
	@PathVariable(value = "id") Long refereeId) {
		return new ResponseEntity<>(this.refereeService.getRefereeById(refereeId), HttpStatus.OK);
	}

	@ApiOperation(value = "Get referee by Name", response = RefereeResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Referee Details Retrieved",response=RefereeResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to View the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Reach is Forbidden"),
    		@ApiResponse(code=404,message="Referee Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/referee/name/{name}", produces = "application/json")
	public ResponseEntity<List<RefereeResponse>> getRefereeByName(@ApiParam(value = "the referee's name", required = true)
	@NotEmpty(message = "Referee's name is mandatory")
	@Size(max = 50, message = "Referee's name should not be greater than 50 characters")
	@PathVariable(value = "name") String refereeName) {
		return new ResponseEntity<>(this.refereeService.getRefereeByName(refereeName), HttpStatus.OK);
	}

	@ApiOperation(value = "Get referee by Document", response = RefereeResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Referee Details Retrieved",response=RefereeResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to View the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Reach is Forbidden"),
    		@ApiResponse(code=404,message="Referee Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/referee/document/{document}", produces = "application/json")
	public ResponseEntity<RefereeResponse> getRefereeByDocument(@ApiParam(value = "the referee's document", required = true)
	@NotEmpty(message = "Referee's document is mandatory")
	@Size(max = 25, message = "Referee's document should not be greater than 25 characters")
	@PathVariable(value = "document") String refereeDocument) {
		return new ResponseEntity<>(this.refereeService.getRefereeByDocument(refereeDocument), HttpStatus.OK);
	}

	@ApiOperation(value = "Create referee", response = RefereeResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Referee Details Created",response=RefereeResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PostMapping(value = "/referee", produces = "application/json")
	public ResponseEntity<RefereeResponse> createReferee(@ApiParam(value = "a referee object", required = true)
		@Valid @RequestBody RefereeRequest refereeRequest) {
		return new ResponseEntity<>(this.refereeService.createReferee(refereeRequest), HttpStatus.OK);
	}

	@ApiOperation(value = "Update referee by Id", response = RefereeResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Referee Details Updated",response=RefereeResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to Update the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Update is Forbidden"),
    		@ApiResponse(code=404,message="Referee Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PutMapping(value = "/referee/{id}", produces = "application/json")
	public ResponseEntity<RefereeResponse> updateRefereeById(@ApiParam(value = "the referee's identification", required = true)
	@NotNull(message = "Referee's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Referee identification should be numeric")
	@PathVariable(value = "id") Long refereeId, @ApiParam(value = "a referee object", required = true)
		@NotNull @RequestBody Map<String, Object> refereeParameters) {
		return new ResponseEntity<>(this.refereeService.updateRefereeById(refereeId, refereeParameters), HttpStatus.OK);
	}

	@ApiOperation(value = "Delete referee by Id", response = String.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Referee Details Deleted",response=String.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to Delete the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Delete is Forbidden"),
    		@ApiResponse(code=404,message="Referee Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@DeleteMapping(value = "/referee/{id}", produces = "application/json")
	public ResponseEntity<String> deleteRefereeById(@ApiParam(value = "the referee's identification", required = true)
	@NotNull(message = "Referee identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Referee identification should be numeric")
	@PathVariable(value = "id") Long refereeId) {
		return new ResponseEntity<>(this.refereeService.deleteRefereeById(refereeId), HttpStatus.OK);
	}

	@ApiOperation(value = "Get list of matches by referee", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of matches by referee", response = List.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "You Are Not Authorized to View the Resource"),
			@ApiResponse(code = 403, message = "Accessing the Resource you Were Trying to Reach is Forbidden"),
			@ApiResponse(code = 404, message = "Competition-Season Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/referee/{id}/match", produces = "application/json")
	public ResponseEntity<List<MatchSimpleResponse>> getMatchesByReferee(
			@ApiParam(value = "Referee's identification", required = true)
			@NotNull(message = "Referee's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Referee's identification should be numeric")
			@PathVariable(value = "id") Long refereeId) {
		return new ResponseEntity<>(this.refereeService.getAllMatches(refereeId),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Get list of matches by referee and status", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of matches by referee with a status", response = List.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "You Are Not Authorized to View the Resource"),
			@ApiResponse(code = 403, message = "Accessing the Resource you Were Trying to Reach is Forbidden"),
			@ApiResponse(code = 404, message = "Competition-Season Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/referee/{id}/match/status/{matchStatus}", produces = "application/json")
	public ResponseEntity<List<MatchSimpleResponse>> getMatchesByRefereeAndStatus(
			@ApiParam(value = "Referee's identification", required = true)
			@NotNull(message = "Referee's identification is mandatory")
			@Digits(integer=10, fraction=0, message = "Referee's identification should be numeric")
			@PathVariable(value = "id") Long refereeId, @ApiParam(value = "Match's status", required = true)
			@NotEmpty(message = "Match's status is mandatory")
			@PathVariable(value = "matchStatus")String matchStatus) {
		return new ResponseEntity<>(this.refereeService.getAllMatchesByStatus(refereeId, matchStatus),
				HttpStatus.OK);
	}

}
