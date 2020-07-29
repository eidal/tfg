package com.leaguemanagement.leaguemanagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leaguemanagement.commons.leaguebase.dto.enums.MatchStatus;
import com.leaguemanagement.leaguemanagement.entity.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
	List<Match> findByDateMatchBetween(Date dateStart, Date dateEnd);
	List<Match> findByStatus(MatchStatus status);
	List<Match> findByFieldNameContainingIgnoreCase(String fieldName);
	List<Match> findByFieldNameContainingIgnoreCaseAndStatus(String fieldName, MatchStatus status);
}
