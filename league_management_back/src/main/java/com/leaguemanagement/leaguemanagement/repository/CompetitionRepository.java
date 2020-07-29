package com.leaguemanagement.leaguemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leaguemanagement.leaguemanagement.entity.Competition;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
	List<Competition> findByNameContainingIgnoreCase(String name);
}
