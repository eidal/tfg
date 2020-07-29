package com.leaguemanagement.leaguemanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leaguemanagement.leaguemanagement.entity.CompetitionSeason;
import com.leaguemanagement.leaguemanagement.entity.Round;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
	Optional<Round> findByCompetitionSeasonAndNumber(CompetitionSeason competitionSeason, Integer roundNumber);
}
