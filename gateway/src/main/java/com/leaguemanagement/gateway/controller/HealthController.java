package com.leaguemanagement.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

  @GetMapping(value = "/health", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Void> healthCheck() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

}