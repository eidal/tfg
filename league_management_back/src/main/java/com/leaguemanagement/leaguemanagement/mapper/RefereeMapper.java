package com.leaguemanagement.leaguemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.leaguemanagement.commons.leaguebase.dto.request.RefereeRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.RefereeResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.RefereeSimpleResponse;
import com.leaguemanagement.leaguemanagement.entity.Referee;

@Mapper
public interface RefereeMapper {

	RefereeResponse refereeToRefereeResponse(Referee referee);

	RefereeSimpleResponse refereeToRefereeSimpleResponse(Referee referee);

	Referee refereeRequestToReferee(RefereeRequest refereeRequest);

	List<RefereeResponse> refereeListToRefereeResponseList(List<Referee> refereeList);

	List<RefereeSimpleResponse> refereeListToRefereeSimpleResponseList(List<Referee> refereeList);
}
