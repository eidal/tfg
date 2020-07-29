package com.leaguemanagement.leaguemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.leaguemanagement.commons.leaguebase.dto.request.TeamRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.TeamResponse;
import com.leaguemanagement.leaguemanagement.entity.Team;

@Mapper
public interface TeamMapper {

	TeamResponse teamToTeamResponse(Team Team);

	Team teamRequestToTeam(TeamRequest TeamRequest);

	List<TeamResponse> teamListToTeamResponseList(List<Team> TeamList);
}
