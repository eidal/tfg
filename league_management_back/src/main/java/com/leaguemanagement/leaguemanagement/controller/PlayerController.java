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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leaguemanagement.commons.leaguebase.dto.request.PlayerRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.PlayerResponse;
import com.leaguemanagement.leaguemanagement.service.PlayerService;
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
public class PlayerController extends MainController {

	private final PlayerService playerService;

	public PlayerController(PlayerService playerService) {
		this.playerService = playerService;
	}

	@ApiOperation(value = "Get list players", response = List.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Players Details Retrieved",response=List.class),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/player", produces = "application/json")
	public ResponseEntity<List<PlayerResponse>> getAllPlayers() {
		return new ResponseEntity<>(this.playerService.getAllPlayers(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get player by Id", response = PlayerResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Player Details Retrieved",response=PlayerResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to View the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Reach is Forbidden"),
    		@ApiResponse(code=404,message="Player Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/player/{id}", produces = "application/json")
	public ResponseEntity<PlayerResponse> getPlayerById(@ApiParam(value = "the player's identification", required = true)
	@NotNull(message = "Player's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Player identification should be numeric")
	@PathVariable(value = "id") Long playerId) {
		return new ResponseEntity<>(this.playerService.getPlayerById(playerId), HttpStatus.OK);
	}

	@RequestMapping(value = "/player/document/{document}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<PlayerResponse> getPlayerByDocument(@ApiParam(value = "the player's document", required = true)
	@NotEmpty(message = "Player's document is mandatory")
	@Size(max = 25, message = "Player's document should not be greater than 25 characters")
	@PathVariable(value = "document") String document) {
		return new ResponseEntity<>(this.playerService.getPlayerByDocument(document), HttpStatus.OK);
	}

	@ApiOperation(value = "Get list players by name", response = List.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="List Player's Details Retrieved",response=List.class),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/player/name/{name}", produces = "application/json")
	public ResponseEntity<List<PlayerResponse>> getPlayerByName(@ApiParam(value = "the player's name", required = true)
	@NotEmpty(message = "Player's name is mandatory")
	@Size(max = 50, message = "Player's name should not be greater than 50 characters")
	@PathVariable(value = "name") String name) {
		return new ResponseEntity<>(this.playerService.getPlayerByName(name), HttpStatus.OK);
	}

	@ApiOperation(value = "Get list players by nationality", response = List.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="List Player's Details Retrieved",response=List.class),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@GetMapping(value = "/player/nationality/{nationality}", produces = "application/json")
	public ResponseEntity<List<PlayerResponse>> getPlayerByNationality(@ApiParam(value = "the player's nationality", required = true)
	@NotEmpty(message = "Player's name is mandatory")
	@Size(max = 50, message = "Player's nationality should not be greater than 30 characters")
	@PathVariable(value = "nationality") String nationality) {
		return new ResponseEntity<>(this.playerService.getPlayerByNationality(nationality), HttpStatus.OK);
	}

	@ApiOperation(value = "Create player", response = PlayerResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Player Details Created",response=PlayerResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PostMapping(value = "/player", produces = "application/json")
	public ResponseEntity<PlayerResponse> createPlayer(@ApiParam(value = "a player object", required = true)
		@Valid @RequestBody PlayerRequest playerRequest) {
		return new ResponseEntity<>(this.playerService.createPlayer(playerRequest), HttpStatus.OK);
	}

	@ApiOperation(value = "Update player by Id", response = PlayerResponse.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Player Details Updated",response=PlayerResponse.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to Update the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Update is Forbidden"),
    		@ApiResponse(code=404,message="Player Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@PutMapping(value = "/player/{id}", produces = "application/json")
	public ResponseEntity<PlayerResponse> updatePlayerById(@ApiParam(value = "the player's identification", required = true)
	@NotNull(message = "Player's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Player identification should be numeric")
	@PathVariable(value = "id") Long playerId, @ApiParam(value = "a player object", required = true)
		@NotNull @RequestBody Map<String, Object> playerParameters) {
		return new ResponseEntity<>(this.playerService.updatePlayerById(playerId, playerParameters), HttpStatus.OK);
	}

	@ApiOperation(value = "Delete player by Id", response = String.class)
    @ApiResponses(value={
    		@ApiResponse(code=200,message="Player Details Deleted",response=String.class),
    		@ApiResponse(code=400,message="Bad Request"),
    		@ApiResponse(code=401,message="You Are Not Authorized to Delete the Resource"),
    		@ApiResponse(code=403,message="Accessing the Resource you Were Trying to Delete is Forbidden"),
    		@ApiResponse(code=404,message="Player Not Found"),
    		@ApiResponse(code=500,message="Internal Server Error")
    		})
	@DeleteMapping(value = "/player/{id}", produces = "application/json")
	public ResponseEntity<String> deletePlayerById(@ApiParam(value = "the player's identification", required = true)
	@NotNull(message = "Player's identification is mandatory")
	@Digits(integer=10, fraction=0, message = "Player identification should be numeric")
	@PathVariable(value = "id") Long playerId) {
		return new ResponseEntity<>(this.playerService.deletePlayerById(playerId), HttpStatus.OK);
	}
}