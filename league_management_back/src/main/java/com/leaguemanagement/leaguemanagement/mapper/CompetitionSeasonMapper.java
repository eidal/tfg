package com.leaguemanagement.leaguemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.leaguemanagement.commons.leaguebase.dto.response.CompetitionSeasonResponse;
import com.leaguemanagement.leaguemanagement.entity.CompetitionSeason;

@Mapper
public interface CompetitionSeasonMapper {

	@Mapping(target = "competitionId", source = "competitionSeason.competition.id")
	@Mapping(target = "competitionName", source = "competitionSeason.competition.name")
	@Mapping(target = "seasonId", source = "competitionSeason.season.id")
	CompetitionSeasonResponse competitionSeasonToCompetitionSeasonResponse(CompetitionSeason competitionSeason);

	List<CompetitionSeasonResponse> competitionSeasonListToCompetitionSeasonResponseList(List<CompetitionSeason> competitionSeasonList);
}
