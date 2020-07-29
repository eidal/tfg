package com.leaguemanagement.leaguemanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leaguemanagement.leaguemanagement.entity.Season;
import com.leaguemanagement.leaguemanagement.entity.Team;
import com.leaguemanagement.leaguemanagement.entity.TeamSeason;

@Repository
public interface TeamSeasonRepository extends JpaRepository<TeamSeason, Long> {
	Optional<TeamSeason> findByTeamAndSeason(Team team, Season season);
}
