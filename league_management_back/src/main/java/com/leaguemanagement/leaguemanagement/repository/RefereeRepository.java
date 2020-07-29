package com.leaguemanagement.leaguemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leaguemanagement.leaguemanagement.entity.Referee;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Long> {
	Referee findByDocumentIgnoreCase(String document);
	List<Referee> findByNameContainingIgnoreCase(String name);
}
