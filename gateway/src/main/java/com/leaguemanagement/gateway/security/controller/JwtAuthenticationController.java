package com.leaguemanagement.gateway.security.controller;

import com.leaguemanagement.gateway.security.request.JwtRequest;
import com.leaguemanagement.gateway.security.response.JwtResponse;
import com.leaguemanagement.gateway.security.service.JwtUserDetailsService;
import com.leaguemanagement.gateway.security.utils.JwtTokenUtil;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
@Validated
public class JwtAuthenticationController {

  private final AuthenticationManager authenticationManager;

  private final JwtTokenUtil jwtTokenUtil;

  private final JwtUserDetailsService userDetailsService;

  @PostMapping(value = "/authenticate")
  public ResponseEntity<?> createToken(@RequestBody @Valid JwtRequest jwtRequest) throws Exception {

    authenticate(jwtRequest.getUser(), jwtRequest.getPassword());

    final UserDetails userDetails =
            userDetailsService.loadUserByUsername(jwtRequest.getUser());

    final String token = jwtTokenUtil.generateToken(userDetails);

    return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
  }

  private void authenticate(String user, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }
}