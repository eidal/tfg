package com.leaguemanagement.leaguemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leaguemanagement.leaguemanagement.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
	List<Team> findByNameContainingIgnoreCase(String name);
}
