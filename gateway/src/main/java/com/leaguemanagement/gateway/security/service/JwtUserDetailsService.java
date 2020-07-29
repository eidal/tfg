package com.leaguemanagement.gateway.security.service;

import com.leaguemanagement.gateway.security.repository.UserRepository;
import java.util.Collections;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<com.leaguemanagement.gateway.security.entity.User> userBD
            = userRepository.findById(username);

    if (userBD.isPresent()) {
      return new User(userBD.get().getName(), userBD.get().getPassword(), Collections.emptyList());
    }

    throw new UsernameNotFoundException("User not found with name: " + username);
  }
}