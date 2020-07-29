package com.leaguemanagement.gateway.security.config;

import com.leaguemanagement.gateway.security.service.JwtUserDetailsService;
import com.leaguemanagement.gateway.security.utils.JwtTokenUtil;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtUserDetailsService jwtUserDetailsService;

  private final JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
          throws ServletException, IOException {

    final String requestToken = request.getHeader("Authorization");

    String user = null;
    String token = null;

    if (Objects.nonNull(requestToken) && requestToken.startsWith("Bearer ")) {
      token = requestToken.substring(7);
      try {
        user = jwtTokenUtil.getUserFromToken(token);
      } catch (IllegalArgumentException e) {
        System.out.println("Unable to get JWT Token");
      } catch (ExpiredJwtException e) {
        System.out.println("JWT Token has expired");
      }
    } else {
      logger.warn("JWT Token does not begin with Bearer String");
    }

    if (Objects.nonNull(user)
            && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {

      UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user);

      if (jwtTokenUtil.validateToken(token, userDetails)) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    chain.doFilter(request, response);
  }
}