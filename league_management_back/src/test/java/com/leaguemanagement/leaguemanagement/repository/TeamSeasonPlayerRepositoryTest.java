package com.leaguemanagement.leaguemanagement.repository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.leaguemanagement.leaguemanagement.annotation.IntegrationTest;
import com.leaguemanagement.leaguemanagement.entity.Player;
import com.leaguemanagement.leaguemanagement.entity.Season;
import com.leaguemanagement.leaguemanagement.entity.Team;
import com.leaguemanagement.leaguemanagement.entity.TeamSeason;
import com.leaguemanagement.leaguemanagement.entity.TeamSeasonPlayer;

@RunWith(SpringRunner.class)
@IntegrationTest
public class TeamSeasonPlayerRepositoryTest {

	@Autowired
	TeamSeasonPlayerRepository teamSeasonPlayerRepository;

	@Autowired
	TeamSeasonRepository teamSeasonRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	SeasonRepository seasonRepository;

	@Autowired
	PlayerRepository playerRepository;

	@Test
	public void test_find_team_season_player_by_dorsal_number_as_expected() {
		Player player = new Player();
		player.setDocument("12345A");
		player.setName("Benzemalo");
		player.setNationality("French");
		Team team = new Team();
		team.setName("Reale Madride");
		Season season = new Season();
		season.setYearStart(2017);
		season.setYearEnd(2018);
		Player playerAux = this.playerRepository.save(player);
		Team teamAux = this.teamRepository.save(team);
		Season seasonAux = this.seasonRepository.save(season);
		TeamSeason teamSeason = new TeamSeason();
		teamSeason.setTeam(teamAux);
		teamSeason.setSeason(seasonAux);
		teamSeason.setCoachName("Zinedine TeEnteraz");
		TeamSeason teamSeasonAux = this.teamSeasonRepository.save(teamSeason);
		TeamSeasonPlayer teamSeasonPlayer = new TeamSeasonPlayer();
		teamSeasonPlayer.setActive(true);
		teamSeasonPlayer.setDorsalNumber(10);
		teamSeasonPlayer.setPlayer(playerAux);
		teamSeasonPlayer.setTeamSeason(teamSeasonAux);
		this.teamSeasonPlayerRepository.save(teamSeasonPlayer);
		List<TeamSeasonPlayer> list1 = this.teamSeasonPlayerRepository.findByTeamSeasonAndDorsalNumberAndActive(teamSeasonAux, 10, true);
		List<TeamSeasonPlayer> list2 = this.teamSeasonPlayerRepository.findByTeamSeasonAndDorsalNumberAndActive(teamSeasonAux, 10, false);
		List<TeamSeasonPlayer> list3 = this.teamSeasonPlayerRepository.findByTeamSeasonAndDorsalNumberAndActive(teamSeasonAux, 5, true);
		List<TeamSeasonPlayer> list4 = this.teamSeasonPlayerRepository.findByTeamSeasonAndDorsalNumberAndActive(teamSeasonAux, 5, false);

		System.out.println("tu flipas en colores");
	}
}
