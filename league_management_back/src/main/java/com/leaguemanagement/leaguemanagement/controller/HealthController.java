package com.leaguemanagement.leaguemanagement.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = { "HEALTH" })
public class HealthController {

  @ApiResponse(code = 200, message = "Success")
  @ApiOperation(value = "Health-status microservice")
  @GetMapping(value = "/health", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Void> healthCheck() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

}