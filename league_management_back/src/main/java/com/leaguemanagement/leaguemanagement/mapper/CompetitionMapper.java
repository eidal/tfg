package com.leaguemanagement.leaguemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.leaguemanagement.commons.leaguebase.dto.request.CompetitionRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.CompetitionResponse;
import com.leaguemanagement.leaguemanagement.entity.Competition;

@Mapper
public interface CompetitionMapper {

	CompetitionResponse competitionToCompetitionResponse(Competition competition);

	Competition competitionRequestToCompetition(CompetitionRequest competitionRequest);

	List<CompetitionResponse> competitionListToCompetitionResponseList(List<Competition> competitionList);
}
