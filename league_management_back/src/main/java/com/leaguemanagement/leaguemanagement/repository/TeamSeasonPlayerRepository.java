package com.leaguemanagement.leaguemanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leaguemanagement.leaguemanagement.entity.Player;
import com.leaguemanagement.leaguemanagement.entity.TeamSeason;
import com.leaguemanagement.leaguemanagement.entity.TeamSeasonPlayer;

@Repository
public interface TeamSeasonPlayerRepository extends JpaRepository<TeamSeasonPlayer, Long> {
	Optional<TeamSeasonPlayer> findByTeamSeasonAndPlayer(TeamSeason teamSeason, Player player);
	List<TeamSeasonPlayer> findByTeamSeason(TeamSeason teamSeason);
	List<TeamSeasonPlayer> findByTeamSeasonAndDorsalNumberAndActive(TeamSeason teamSeason, Integer dorsalNumber, Boolean active);
}
