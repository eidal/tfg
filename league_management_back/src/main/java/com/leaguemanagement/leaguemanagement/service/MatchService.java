package com.leaguemanagement.leaguemanagement.service;

import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.leaguemanagement.commons.leaguebase.dto.enums.MatchStatus;
import com.leaguemanagement.commons.leaguebase.dto.request.MatchRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.MatchResponse;
import com.leaguemanagement.leaguemanagement.entity.Match;
import com.leaguemanagement.leaguemanagement.entity.Referee;
import com.leaguemanagement.leaguemanagement.entity.Round;
import com.leaguemanagement.leaguemanagement.entity.TeamSeason;
import com.leaguemanagement.leaguemanagement.exception.MatchException;
import com.leaguemanagement.leaguemanagement.mapper.MatchMapper;
import com.leaguemanagement.leaguemanagement.repository.MatchRepository;
import com.leaguemanagement.leaguemanagement.repository.RefereeRepository;
import com.leaguemanagement.leaguemanagement.repository.RoundRepository;
import com.leaguemanagement.leaguemanagement.repository.TeamSeasonRepository;
import com.leaguemanagement.leaguemanagement.utils.LeagueManagementConsts;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = LeagueManagementConsts.MATCH_CACHE_NAME, cacheManager = "expireOneDay")
public class MatchService {

	private final MatchRepository matchRepository;

	private final TeamSeasonRepository teamSeasonRepository;

	private final RefereeRepository refereeRepository;

	private final RoundRepository roundRepository;

	private final MatchMapper matchMapper;

	@Cacheable(cacheNames = LeagueManagementConsts.MATCH_CACHE_NAME, cacheManager = "expireOneDay",
					key = "{ #root.methodName: #matchId }")
	public MatchResponse getMatchById(@NotNull Long matchId) {
		Match match = this.getMatch(matchId);

		return this.matchMapper.matchToMatchResponse(match);
	}

	public MatchResponse createMatch(@Valid MatchRequest matchRequest) {
			Match match =  new Match();
			match.setDateMatch(matchRequest.getDateMatch());
			match.setHomeTeam(this.getTeamSeason(matchRequest.getHomeTeamId()));
			match.setVisitantTeam(this.getTeamSeason(matchRequest.getVisitantTeamId()));
			match.setReferee(this.getReferee(matchRequest.getRefereeId()));
			match.setRound(this.getRound(matchRequest.getRoundId()));
			match.setFieldName(matchRequest.getFieldName());

			return this.matchMapper.matchToMatchResponse(matchRepository.save(match));
	}

	@CacheEvict(value = LeagueManagementConsts.MATCH_CACHE_NAME, cacheManager = "expireOneDay",
					key = "'getMatchById: ' + #matchId")
	public String deleteMatchById(@NotNull Long matchId) {
		if(this.isMatchModificable(matchId)) {
			try {
				this.matchRepository.deleteById(matchId);
			} catch (EmptyResultDataAccessException e) {
				MatchService.log.error("Error! The match with id {} not exists", matchId);
				throw new MatchException("Error! The match not exists", e);
			}
			return "The match was deleted correctly!";
		}
		throw new MatchException("Error! The match is almost started, impossible to delete");
	}

	@CacheEvict(value = LeagueManagementConsts.MATCH_CACHE_NAME, cacheManager = "expireOneDay",
					key = "'getMatchById: ' + #matchId")
	public MatchResponse updateMatchById(@NotNull Long matchId, @Valid MatchRequest matchRequest) {
		Match match = this.getMatch(matchId);
		if(this.isMatchModificable(matchId)) {
			if(Objects.nonNull(matchRequest.getDateMatch())) {
				match.setDateMatch(matchRequest.getDateMatch());
			}
			if(Objects.nonNull(matchRequest.getHomeTeamId())) {
				match.setHomeTeam(this.getTeamSeason(matchRequest.getHomeTeamId()));
			}
			if(Objects.nonNull(matchRequest.getVisitantTeamId())) {
				match.setVisitantTeam(this.getTeamSeason(matchRequest.getVisitantTeamId()));
			}
			if(Objects.nonNull(matchRequest.getRefereeId())) {
				match.setReferee(this.getReferee(matchRequest.getRefereeId()));
			}
			if(Objects.nonNull(matchRequest.getRoundId())) {
				match.setRound(this.getRound(matchRequest.getRoundId()));
			}
		}
		if(Objects.nonNull(matchRequest.getFieldName())) {
			match.setFieldName(matchRequest.getFieldName());
		}
		Match matchaux=matchRepository.saveAndFlush(match);

		return this.matchMapper.matchToMatchResponse(matchaux);
	}

	private Match getMatch(@NotNull Long matchId) {
		Optional<Match> match = this.matchRepository.findById(matchId);
		if(match.isPresent()) {
			return match.get();
		} else {
			MatchService.log.error("Error! The match with id {} not exists", matchId);
			throw new MatchException("Error! The match not exists");
		}
	}

	private Boolean isMatchModificable(@NotNull Long matchId) {
		Optional<Match> match = this.matchRepository.findById(matchId);
		if(match.isPresent()) {
			return match.get().getStatus().equals(MatchStatus.PENDING);
		} else {
			MatchService.log.error("Error! The match with id {} not exists", matchId);
			throw new MatchException("Error! The match not exists");
		}
	}

	private TeamSeason getTeamSeason(@NotNull Long teamSeasonId) {
		Optional<TeamSeason> teamSeason = this.teamSeasonRepository.findById(teamSeasonId);
		if(teamSeason.isPresent()) {
			return teamSeason.get();
		} else {
			MatchService.log.error("Error! The teamSeason with id {} not exists", teamSeasonId);
			throw new MatchException("Error! The teamSeason not exists, the match is not created");
		}
	}

	private Referee getReferee(@NotNull Long refereeId) {
		Optional<Referee> referee = this.refereeRepository.findById(refereeId);
		if(referee.isPresent()) {
			return referee.get();
		} else {
			MatchService.log.error("Error! The referee with id {} not exists", refereeId);
			throw new MatchException("Error! The referee not exists, the match is not created");
		}
	}

	private Round getRound(@NotNull Long roundId) {
		Optional<Round> round = this.roundRepository.findById(roundId);
		if(round.isPresent()) {
			return round.get();
		} else {
			MatchService.log.error("Error! The round with id {} not exists", roundId);
			throw new MatchException("Error! The round not exists, the match is not created");
		}
	}
}
