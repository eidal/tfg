package com.leaguemanagement.leaguemanagement.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.leaguemanagement.commons.leaguebase.dto.request.TeamSeasonRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.MatchSimpleResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.TeamSeasonResponse;
import com.leaguemanagement.leaguemanagement.entity.Season;
import com.leaguemanagement.leaguemanagement.entity.Team;
import com.leaguemanagement.leaguemanagement.entity.TeamSeason;
import com.leaguemanagement.leaguemanagement.exception.TeamException;
import com.leaguemanagement.leaguemanagement.exception.TeamSeasonException;
import com.leaguemanagement.leaguemanagement.mapper.MatchMapper;
import com.leaguemanagement.leaguemanagement.mapper.TeamSeasonMapper;
import com.leaguemanagement.leaguemanagement.repository.SeasonRepository;
import com.leaguemanagement.leaguemanagement.repository.TeamRepository;
import com.leaguemanagement.leaguemanagement.repository.TeamSeasonRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class TeamSeasonService {

	private final TeamRepository teamRepository;
	private final SeasonRepository seasonRepository;
	private final TeamSeasonRepository teamSeasonRepository;
	private final MatchMapper matchMapper;
	private final TeamSeasonMapper teamSeasonMapper;

	public TeamSeasonResponse createTeamSeason(@NotNull Long teamId, @NotNull Long seasonId, @Valid TeamSeasonRequest teamSeasonRequest) {
		if(!this.existsTeamSeason(teamId, seasonId)) {
			Optional<Team> team = this.teamRepository.findById(teamId);
			Optional<Season> season = this.seasonRepository.findById(seasonId);
			if(team.isPresent() && season.isPresent()) {
				TeamSeason teamSeason = new TeamSeason();
				teamSeason.setTeam(team.get());
				teamSeason.setSeason(season.get());
				teamSeason.setPresidentName(Objects.nonNull(teamSeasonRequest.getPresidentName()) ?
						teamSeasonRequest.getPresidentName() : null);
				teamSeason.setCoachName(Objects.nonNull(teamSeasonRequest.getCoachName()) ?
						teamSeasonRequest.getCoachName() : null);
				teamSeason.setFirstEquipmentDescription(Objects.nonNull(teamSeasonRequest.getFirstEquipmentDescription()) ?
						teamSeasonRequest.getFirstEquipmentDescription() : null);
				teamSeason.setSecondEquipmentDescription(Objects.nonNull(teamSeasonRequest.getSecondEquipmentDescription()) ?
						teamSeasonRequest.getSecondEquipmentDescription() : null);
				return this.teamSeasonMapper.teamSeasonToTeamSeasonResponse(this.teamSeasonRepository.save(teamSeason));

			} else {
				throw new TeamException("Error! The association between team and season is not possible.");
			}
		}
		throw new TeamException("Error! The association between team and season already exists");

	}

	public TeamSeasonResponse getTeamSeason(@NotNull Long teamId, @NotNull Long seasonId) {
		return this.teamSeasonMapper.teamSeasonToTeamSeasonResponse(this.findTeamSeason(teamId, seasonId));
	}

	public TeamSeasonResponse updateTeamSeason(@NotNull Long teamId, @NotNull Long seasonId, @NotNull Map<String, Object> teamSeasonParameters) {
		TeamSeason teamSeason = this.findTeamSeason(teamId, seasonId);
		try {
			BeanUtils.populate(teamSeason, teamSeasonParameters);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Error! The update fail, try again later!");
		}

		TeamSeason teamseasonaux = teamSeasonRepository.saveAndFlush(teamSeason);

		return this.teamSeasonMapper.teamSeasonToTeamSeasonResponse(teamseasonaux);
	}

	public List<MatchSimpleResponse> getAllHomeMatches(@NotNull Long teamId, @NotNull Long seasonId) {
		return this.matchMapper.matchListToMatchSimpleResponseList(
						new ArrayList<>(this.findTeamSeason(teamId, seasonId).getHomeMatch()));
	}

	public List<MatchSimpleResponse> getAllHomeMatchesByStatus(@NotNull Long teamId, @NotNull Long seasonId, String matchStatus) {
		return this.matchMapper.matchListToMatchSimpleResponseList(
				this.findTeamSeason(teamId, seasonId).getHomeMatch().stream()
				.filter(match -> match.getStatus().name().equals(matchStatus)).collect(Collectors.toList()));
	}

	public List<MatchSimpleResponse> getAllVisitantMatches(@NotNull Long teamId, @NotNull Long seasonId) {
		return this.matchMapper.matchListToMatchSimpleResponseList(
						new ArrayList<>(this.findTeamSeason(teamId, seasonId).getVisitantMatch()));
	}

	public List<MatchSimpleResponse> getAllVisitantMatchesByStatus(@NotNull Long teamId, @NotNull Long seasonId,
																   String matchStatus) {
		return this.matchMapper.matchListToMatchSimpleResponseList(
				this.findTeamSeason(teamId, seasonId).getVisitantMatch().stream()
				.filter(match -> match.getStatus().name().equals(matchStatus)).collect(Collectors.toList()));
	}

	private TeamSeason findTeamSeason(Long teamId, Long seasonId) {
		Optional<Team> team = this.teamRepository.findById(teamId);
		Optional<Season> season = this.seasonRepository.findById(seasonId);
		if(team.isPresent() && season.isPresent()) {
			Optional<TeamSeason> teamSeason = this.teamSeasonRepository.findByTeamAndSeason(team.get(), season.get());
			if(teamSeason.isPresent()) {
				return teamSeason.get();
			}
		}
		throw new TeamSeasonException("Error! The team or the season not exists!");
	}

	private Boolean existsTeamSeason(@NotNull Long teamId, @NotNull Long seasonId) {
		Optional<Team> team = this.teamRepository.findById(teamId);
		Optional<Season> season = this.seasonRepository.findById(seasonId);
		if(team.isPresent() && season.isPresent()) {
			Optional<TeamSeason> teamSeason = this.teamSeasonRepository.findByTeamAndSeason(team.get(), season.get());
			return teamSeason.isPresent();
		}
		throw new TeamSeasonException("Error! The team or the season not exists!");
	}
}
