package com.leaguemanagement.leaguemanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leaguemanagement.leaguemanagement.entity.Competition;
import com.leaguemanagement.leaguemanagement.entity.CompetitionSeason;
import com.leaguemanagement.leaguemanagement.entity.Season;

@Repository
public interface CompetitionSeasonRepository extends JpaRepository<CompetitionSeason, Long> {
	Optional<CompetitionSeason> findByCompetitionAndSeason(Competition competition, Season season);
}
