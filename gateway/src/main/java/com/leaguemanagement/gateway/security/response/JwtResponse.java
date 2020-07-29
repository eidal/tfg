package com.leaguemanagement.gateway.security.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtResponse implements Serializable {

  private final String token;
}