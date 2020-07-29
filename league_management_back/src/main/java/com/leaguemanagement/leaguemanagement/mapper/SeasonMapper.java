package com.leaguemanagement.leaguemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.leaguemanagement.commons.leaguebase.dto.request.SeasonRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.SeasonResponse;
import com.leaguemanagement.leaguemanagement.entity.Season;

@Mapper
public interface SeasonMapper {

	SeasonResponse seasonToSeasonResponse(Season season);

	Season seasonRequestToSeason(SeasonRequest seasonRequest);

	List<SeasonResponse> seasonListToSeasonResponseList(List<Season> seasonList);
}
