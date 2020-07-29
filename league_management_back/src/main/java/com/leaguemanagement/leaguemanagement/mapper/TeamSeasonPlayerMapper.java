package com.leaguemanagement.leaguemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.leaguemanagement.commons.leaguebase.dto.response.TeamSeasonPlayerResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.TeamSeasonPlayerSimpleResponse;
import com.leaguemanagement.leaguemanagement.entity.TeamSeasonPlayer;

@Mapper
public interface TeamSeasonPlayerMapper {

	@Mapping(target = "teamId", source = "teamSeasonPlayer.teamSeason.team.id")
	@Mapping(target = "teamName", source = "teamSeasonPlayer.teamSeason.team.name")
	@Mapping(target = "seasonId", source = "teamSeasonPlayer.teamSeason.season.id")
	@Mapping(target = "playerId", source = "teamSeasonPlayer.player.id")
	@Mapping(target = "playerName", source = "teamSeasonPlayer.player.name")
	TeamSeasonPlayerResponse teamSeasonPlayerToTeamSeasonPlayerResponse(TeamSeasonPlayer teamSeasonPlayer);

	List<TeamSeasonPlayerResponse> teamSeasonPlayerListToTeamSeasonPlayerResponseList(List<TeamSeasonPlayer> teamSeasonPlayerList);

	@Mapping(target = "playerId", source = "teamSeasonPlayer.player.id")
	@Mapping(target = "playerName", source = "teamSeasonPlayer.player.name")
	TeamSeasonPlayerSimpleResponse teamSeasonPlayerToTeamSeasonPlayerSimpleResponse(TeamSeasonPlayer teamSeasonPlayer);

	List<TeamSeasonPlayerSimpleResponse> teamSeasonPlayerListToTeamSeasonPlayerSimpleResponseList(List<TeamSeasonPlayer> teamSeasonPlayerList);
}
