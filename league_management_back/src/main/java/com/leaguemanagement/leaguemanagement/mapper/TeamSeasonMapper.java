package com.leaguemanagement.leaguemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.leaguemanagement.commons.leaguebase.dto.response.TeamSeasonResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.TeamSeasonSimpleResponse;
import com.leaguemanagement.leaguemanagement.entity.TeamSeason;

@Mapper
public interface TeamSeasonMapper {

	@Mapping(target = "teamId", source = "teamSeason.team.id")
	@Mapping(target = "teamName", source = "teamSeason.team.name")
	@Mapping(target = "seasonId", source = "teamSeason.season.id")
	TeamSeasonResponse teamSeasonToTeamSeasonResponse(TeamSeason teamSeason);

	@Mapping(target = "teamId", source = "teamSeason.team.id")
	@Mapping(target = "teamName", source = "teamSeason.team.name")
	TeamSeasonSimpleResponse teamSeasonToTeamSeasonSimpleResponse(TeamSeason teamSeason);

	List<TeamSeasonResponse> teamSeasonListToTeamSeasonResponseList(List<TeamSeason> teamSeasonList);

	List<TeamSeasonSimpleResponse> teamSeasonListToTeamSeasonSimpleResponseList(List<TeamSeason> teamSeasonList);
}
