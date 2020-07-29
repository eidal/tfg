package com.leaguemanagement.gateway.security.repository;

import com.leaguemanagement.gateway.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
