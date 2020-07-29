package com.leaguemanagement.leaguemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.leaguemanagement.commons.leaguebase.dto.request.MatchRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.MatchResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.MatchSimpleResponse;
import com.leaguemanagement.leaguemanagement.entity.Match;

@Mapper
public interface MatchMapper {

	MatchResponse matchToMatchResponse(Match match);

	MatchSimpleResponse matchToMatchSimpleResponse(Match match);

	Match matchRequestToMatch(MatchRequest matchRequest);

	List<MatchResponse> matchListToMatchResponseList(List<Match> matchList);

	List<MatchSimpleResponse> matchListToMatchSimpleResponseList(List<Match> matchList);
}
