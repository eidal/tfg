package com.leaguemanagement.leaguemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.leaguemanagement.commons.leaguebase.dto.response.RoundResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.RoundSimpleResponse;
import com.leaguemanagement.leaguemanagement.entity.Round;

@Mapper
public interface RoundMapper {

	RoundResponse roundToRoundResponse(Round round);

	RoundSimpleResponse roundToRoundSimpleResponse(Round round);

	List<RoundResponse> roundListToRoundResponseList(List<Round> roundList);

	List<RoundSimpleResponse> roundListToRoundSimpleResponseList(List<Round> roundList);
}
