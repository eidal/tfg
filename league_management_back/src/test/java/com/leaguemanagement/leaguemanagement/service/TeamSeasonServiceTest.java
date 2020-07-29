package com.leaguemanagement.leaguemanagement.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.leaguemanagement.commons.leaguebase.dto.request.PlayerRequest;
import com.leaguemanagement.commons.leaguebase.dto.request.SeasonRequest;
import com.leaguemanagement.commons.leaguebase.dto.request.TeamRequest;
import com.leaguemanagement.commons.leaguebase.dto.request.TeamSeasonPlayerRequest;
import com.leaguemanagement.commons.leaguebase.dto.request.TeamSeasonRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.TeamSeasonPlayerResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.TeamSeasonResponse;
import com.leaguemanagement.leaguemanagement.annotation.IntegrationTest;
import com.leaguemanagement.leaguemanagement.entity.TeamSeason;

@RunWith(SpringRunner.class)
@IntegrationTest
public class TeamSeasonServiceTest {
	@Autowired
	TeamSeasonService teamSeasonService;
	@Autowired
	TeamService teamService;
	@Autowired
	SeasonService seasonService;
	@Autowired
	PlayerService playerService;
	@Autowired
	TeamSeasonPlayerService teamSeasonPlayerService;

	@Test
	public void test_team_season_vinculation() {
		String teamName = "Real Madrid";
		String coachName = "Zinedine";
		TeamRequest teamRequest = new TeamRequest();
		teamRequest.setName(teamName);
		Long teamId = this.teamService.createTeam(teamRequest).getId();
		SeasonRequest seasonRequest = new SeasonRequest();
		seasonRequest.setYearStart(2017);
		seasonRequest.setYearEnd(2018);
		Long seasonId = this.seasonService.createSeason(seasonRequest).getId();
		TeamSeasonRequest teamSeasonRequest = new TeamSeasonRequest();
		teamSeasonRequest.setCoachName(coachName);
		TeamSeasonResponse teamSeasonResponse = this.teamSeasonService.createTeamSeason(teamId, seasonId, teamSeasonRequest);

		assertEquals(teamSeasonResponse.getTeamId(), teamId);
		assertEquals(teamSeasonResponse.getTeamName(), teamName);
		assertEquals(teamSeasonResponse.getSeasonId(), seasonId);
		assertEquals(teamSeasonResponse.getCoachName(), coachName);
		assertNull(teamSeasonResponse.getPresidentName());
		assertNull(teamSeasonResponse.getFirstEquipmentDescription());
		assertNull(teamSeasonResponse.getSecondEquipmentDescription());

	}

	@Test
	public void test_team_season_player_vinculation() {
		String teamName = "Real Madrid";
		String coachName = "Zinedine";
		String playerName = "Benzemalo";
		String playerDocument = "1234556A";
		String playerNationality = "French";
		Date playerDateBirth = new Date();
		TeamRequest teamRequest = new TeamRequest();
		teamRequest.setName(teamName);
		Long teamId = this.teamService.createTeam(teamRequest).getId();
		SeasonRequest seasonRequest = new SeasonRequest();
		seasonRequest.setYearStart(2017);
		seasonRequest.setYearEnd(2018);
		Long seasonId = this.seasonService.createSeason(seasonRequest).getId();
		TeamSeasonRequest teamSeasonRequest = new TeamSeasonRequest();
		teamSeasonRequest.setCoachName(coachName);
		TeamSeasonResponse teamSeasonResponse = this.teamSeasonService.createTeamSeason(teamId, seasonId, teamSeasonRequest);
		PlayerRequest playerRequest = new PlayerRequest();
		playerRequest.setDocument(playerDocument);
		playerRequest.setDateBirth(playerDateBirth);
		playerRequest.setName(playerName);
		playerRequest.setNationality(playerNationality);
		Long playerId = this.playerService.createPlayer(playerRequest).getId();
		TeamSeasonPlayerRequest teamSeasonPlayerRequest = new TeamSeasonPlayerRequest();
		teamSeasonPlayerRequest.setActive(true);
		teamSeasonPlayerRequest.setDorsalNumber(5);
		@SuppressWarnings("unused")
		TeamSeasonPlayerResponse teamSeasonPlayerResponse = this.teamSeasonPlayerService.createTeamSeasonPlayer(teamId, seasonId, playerId, teamSeasonPlayerRequest);
		TeamSeasonPlayerRequest teamSeasonPlayerRequest2 = new TeamSeasonPlayerRequest();
		teamSeasonPlayerRequest2.setDorsalNumber(10);
		teamSeasonPlayerResponse = this.teamSeasonPlayerService.updateTeamSeasonPlayer(teamId, seasonId, playerId, teamSeasonPlayerRequest2);
		assertEquals(teamSeasonResponse.getTeamId(), teamId);
		assertEquals(teamSeasonResponse.getTeamName(), teamName);
		assertEquals(teamSeasonResponse.getSeasonId(), seasonId);
		assertEquals(teamSeasonResponse.getCoachName(), coachName);
		assertNull(teamSeasonResponse.getPresidentName());
		assertNull(teamSeasonResponse.getFirstEquipmentDescription());
		assertNull(teamSeasonResponse.getSecondEquipmentDescription());

	}


}
