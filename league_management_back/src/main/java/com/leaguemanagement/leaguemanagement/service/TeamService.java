package com.leaguemanagement.leaguemanagement.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.leaguemanagement.commons.leaguebase.dto.request.TeamRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.TeamResponse;
import com.leaguemanagement.leaguemanagement.entity.Team;
import com.leaguemanagement.leaguemanagement.exception.TeamException;
import com.leaguemanagement.leaguemanagement.mapper.TeamMapper;
import com.leaguemanagement.leaguemanagement.mapper.TeamSeasonMapper;
import com.leaguemanagement.leaguemanagement.repository.SeasonRepository;
import com.leaguemanagement.leaguemanagement.repository.TeamRepository;
import com.leaguemanagement.leaguemanagement.repository.TeamSeasonRepository;
import com.leaguemanagement.leaguemanagement.utils.LeagueManagementConsts;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@CacheConfig(cacheNames = LeagueManagementConsts.TEAM_CACHE_NAME, cacheManager = "expireOneDay")
public class TeamService {

	private final TeamRepository teamRepository;
	private final SeasonRepository seasonRepository;
	private final TeamSeasonRepository teamSeasonRepository;
	private final TeamMapper teamMapper;
	private final TeamSeasonMapper teamSeasonMapper;
	private final CacheManager expireOneDayCache;

	public TeamService(final TeamRepository teamRepository, final SeasonRepository seasonRepository,
			final TeamSeasonRepository teamSeasonRepository, final TeamMapper teamMapper,
			final TeamSeasonMapper teamSeasonMapper, final CacheManager cacheManager) {
		this.teamRepository = teamRepository;
		this.seasonRepository = seasonRepository;
		this.teamSeasonRepository = teamSeasonRepository;
		this.teamMapper = teamMapper;
		this.teamSeasonMapper = teamSeasonMapper;
		this.expireOneDayCache = cacheManager;
	}

	@Cacheable(cacheNames = LeagueManagementConsts.TEAM_CACHE_NAME, cacheManager = "expireOneDay",
					key = "{ #root.methodName: #teamId }")
	public TeamResponse getTeamById(@NotNull Long teamId) {
		Team team = this.getTeam(teamId);

		return this.teamMapper.teamToTeamResponse(team);
	}

	@Cacheable(cacheNames = LeagueManagementConsts.TEAM_CACHE_NAME, cacheManager = "expireOneDay",
					key = "{ #root.methodName: #name }")
	public List<TeamResponse> getTeamByName(@NotEmpty String name) {
		return this.teamMapper.teamListToTeamResponseList(this.teamRepository.findByNameContainingIgnoreCase(name));
	}

	public TeamResponse createTeam(@Valid TeamRequest teamRequest) {
		Team team = this.teamRepository.save(
				this.teamMapper.teamRequestToTeam(teamRequest));

		return this.teamMapper.teamToTeamResponse(team);
	}

	@Cacheable(cacheNames = LeagueManagementConsts.TEAM_CACHE_NAME, cacheManager = "expireOneDay",
					key = "{ #root.methodName }")
	public List<TeamResponse> getAllTeams() {
		return this.teamMapper.teamListToTeamResponseList(
				this.teamRepository.findAll());
	}

	public String deleteTeamById(@NotNull Long teamId) {
		Team team = this.getTeam(teamId);
		try {
			this.teamRepository.deleteById(teamId);
		} catch (EmptyResultDataAccessException e) {
			TeamService.log.error("Error! The team with id {} not exists", teamId);
			throw new TeamException("Error! The team not exists", e);
		}
		this.clearTeamCache(team);
		return "The team was deleted correctly!";
	}

	public TeamResponse updateTeamById(@NotNull Long teamId, @NotNull Map<String, Object> teamParameters) {
		Team team = this.getTeam(teamId);
		this.clearTeamCache(team);
		try {
			BeanUtils.populate(team, teamParameters);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Error! The update fail, try again later!");
		}

		try {
			Team teamaux=teamRepository.saveAndFlush(team);
			this.clearTeamCache(teamaux);
			return this.teamMapper.teamToTeamResponse(teamaux);
		} catch (ConstraintViolationException e) {
			TeamService.log.error(e.getMessage());
			throw new TeamException("Error! team's document already exists!", e);
		}

	}

	private Team getTeam(@NotNull Long teamId) {
		Optional<Team> team = this.teamRepository.findById(teamId);
		if(team.isPresent()) {
			return team.get();
		} else {
			TeamService.log.error("Error! The team with id {} not exists", teamId);
			throw new TeamException("Error! The team not exists");
		}
	}

	private void clearTeamCache(Team currentTeam) {
		Optional.ofNullable(expireOneDayCache.getCache(LeagueManagementConsts.TEAM_CACHE_NAME))
						.ifPresent(cache -> {
							cache.evict("getTeamByName:" + currentTeam.getName());
							cache.evict("getAllTeams");
						});
	}
}
