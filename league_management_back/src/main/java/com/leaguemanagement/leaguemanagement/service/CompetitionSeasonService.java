package com.leaguemanagement.leaguemanagement.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.leaguemanagement.commons.leaguebase.dto.request.CompetitionSeasonRequest;
import com.leaguemanagement.commons.leaguebase.dto.request.RoundRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.CompetitionSeasonResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.MatchSimpleResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.RefereeSimpleResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.RoundResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.RoundSimpleResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.TeamSeasonSimpleResponse;
import com.leaguemanagement.leaguemanagement.entity.Competition;
import com.leaguemanagement.leaguemanagement.entity.CompetitionSeason;
import com.leaguemanagement.leaguemanagement.entity.Match;
import com.leaguemanagement.leaguemanagement.entity.Referee;
import com.leaguemanagement.leaguemanagement.entity.Round;
import com.leaguemanagement.leaguemanagement.entity.Season;
import com.leaguemanagement.leaguemanagement.entity.Team;
import com.leaguemanagement.leaguemanagement.entity.TeamSeason;
import com.leaguemanagement.leaguemanagement.exception.CompetitionException;
import com.leaguemanagement.leaguemanagement.exception.CompetitionSeasonException;
import com.leaguemanagement.leaguemanagement.mapper.CompetitionSeasonMapper;
import com.leaguemanagement.leaguemanagement.mapper.MatchMapper;
import com.leaguemanagement.leaguemanagement.mapper.RefereeMapper;
import com.leaguemanagement.leaguemanagement.mapper.RoundMapper;
import com.leaguemanagement.leaguemanagement.mapper.TeamSeasonMapper;
import com.leaguemanagement.leaguemanagement.repository.CompetitionRepository;
import com.leaguemanagement.leaguemanagement.repository.CompetitionSeasonRepository;
import com.leaguemanagement.leaguemanagement.repository.MatchRepository;
import com.leaguemanagement.leaguemanagement.repository.RefereeRepository;
import com.leaguemanagement.leaguemanagement.repository.RoundRepository;
import com.leaguemanagement.leaguemanagement.repository.SeasonRepository;
import com.leaguemanagement.leaguemanagement.repository.TeamRepository;
import com.leaguemanagement.leaguemanagement.repository.TeamSeasonRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class CompetitionSeasonService {

	private final CompetitionRepository competitionRepository;
	private final MatchRepository matchRepository;
	private final RefereeRepository refereeRepository;
	private final RoundRepository roundRepository;
	private final SeasonRepository seasonRepository;
	private final TeamRepository teamRepository;
	private final CompetitionSeasonRepository competitionSeasonRepository;
	private final TeamSeasonRepository teamSeasonRepository;
	private final CompetitionSeasonMapper competitionSeasonMapper;
	private final MatchMapper matchMapper;
	private final RefereeMapper refereeMapper;
	private final RoundMapper roundMapper;
	private final TeamSeasonMapper teamSeasonMapper;

	public CompetitionSeasonResponse createCompetitionSeason(@NotNull Long competitionId, @NotNull Long seasonId, @NotNull @Valid CompetitionSeasonRequest competitionSeasonRequest) {
		if(!this.existsCompetitionSeason(competitionId, seasonId)) {
			Optional<Competition> competition = this.competitionRepository.findById(competitionId);
			Optional<Season> season = this.seasonRepository.findById(seasonId);
			if(competition.isPresent() && season.isPresent()) {
				CompetitionSeason competitionSeason = new CompetitionSeason();
				competitionSeason.setCompetition(competition.get());
				competitionSeason.setSeason(season.get());
				competitionSeason.setTeamsNumber(competitionSeasonRequest.getTeamsNumber());
				competitionSeason.setReturnsNumber(competitionSeasonRequest.getReturnsNumber());
				competitionSeason.setPointsWin(competitionSeasonRequest.getPointsWin());
				competitionSeason.setPointsTie(competitionSeasonRequest.getPointsTie());
				competitionSeason.setRounds();
				return this.competitionSeasonMapper.competitionSeasonToCompetitionSeasonResponse(this.competitionSeasonRepository.save(competitionSeason));
			} else {
				throw new CompetitionException("Error! The association between competition and season is not possible.");
			}
		}
		throw new CompetitionException("Error! The association between competition and season already exists");

	}

	public CompetitionSeasonResponse getCompetitionSeason(@NotNull Long competitionId, @NotNull Long seasonId) {
		return this.competitionSeasonMapper.competitionSeasonToCompetitionSeasonResponse(this.findCompetitionSeason(competitionId, seasonId));
	}

	public CompetitionSeasonResponse updateCompetitionSeason(@NotNull Long competitionId, @NotNull Long seasonId, @NotNull Map<String, Object> competitionSeasonParameters) {
		CompetitionSeason competitionSeason = this.findCompetitionSeason(competitionId, seasonId);
		if(!competitionSeason.getStarted()) {
			try {
				BeanUtils.populate(competitionSeason, competitionSeasonParameters);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException("Error! The update fail, try again later!");
			}
			competitionSeason.setRounds();
			CompetitionSeason competitionseasonaux = competitionSeasonRepository.saveAndFlush(competitionSeason);

			return this.competitionSeasonMapper.competitionSeasonToCompetitionSeasonResponse(competitionseasonaux);
		}
		throw new CompetitionSeasonException("Error! The competition has begun. impossible to make changes!");
	}

	public String vinculateRefereeToCompetitionSeason(@NotNull Long competitionId, @NotNull Long seasonId, @NotNull Long refereeId) {
		CompetitionSeason competitionSeason = this.findCompetitionSeason(competitionId, seasonId);
		Optional<Referee> referee = this.refereeRepository.findById(refereeId);
		if(referee.isPresent()) {
			competitionSeason.addReferee(referee.get());
			this.competitionSeasonRepository.saveAndFlush(competitionSeason);
			return "The referee has vinculated with the competitionSeason correctly";
		}
		throw new CompetitionSeasonException("Error! The competitionSeason or referee not exists!");
	}

	public String vinculateTeamToCompetitionSeason(@NotNull Long competitionId, @NotNull Long seasonId, @NotNull Long teamId) {
		CompetitionSeason competitionSeason = this.findCompetitionSeason(competitionId, seasonId);
		TeamSeason teamSeason = this.findTeamSeason(teamId, seasonId);
		competitionSeason.addTeamSeason(teamSeason);
		this.competitionSeasonRepository.saveAndFlush(competitionSeason);
		return "The team has vinculated with the competitionSeason correctly";
	}

	public List<RefereeSimpleResponse> getAllReferees(@NotNull Long competitionId, @ NotNull Long seasonId) {
		CompetitionSeason competitionSeason = this.findCompetitionSeason(competitionId, seasonId);
		return this.refereeMapper.refereeListToRefereeSimpleResponseList(
						new ArrayList<>(competitionSeason.getReferees()));
	}

	public List<TeamSeasonSimpleResponse> getAllTeamSeasons(@NotNull Long competitionId, @ NotNull Long seasonId) {
		CompetitionSeason competitionSeason = this.findCompetitionSeason(competitionId, seasonId);
		return this.teamSeasonMapper.teamSeasonListToTeamSeasonSimpleResponseList(
						new ArrayList<>(competitionSeason.getTeamSeasons()));
	}

	public List<RoundSimpleResponse> getAllRounds(@NotNull Long competitionId, @ NotNull Long seasonId) {
		CompetitionSeason competitionSeason = this.findCompetitionSeason(competitionId, seasonId);
		return this.roundMapper.roundListToRoundSimpleResponseList(
						new ArrayList<>(competitionSeason.getRounds()));
	}

	public RoundResponse getRoundInACompetitionSeason(@NotNull Long competitionId, @ NotNull Long seasonId, @NotNull Integer roundNumber) {
		return this.roundMapper.roundToRoundResponse(
				this.findRoundInACompetitionSeason(competitionId, seasonId, roundNumber));
	}

	public RoundResponse updateRoundInACompetitionSeason(@NotNull Long competitionId, @ NotNull Long seasonId,
			@NotNull Integer roundNumber, @NotNull RoundRequest roundRequest) {
		Round round = this.findRoundInACompetitionSeason(competitionId, seasonId, roundNumber);
		if(Objects.nonNull(roundRequest.getDescription())) {
			round.setDescription(roundRequest.getDescription());
		}
		if(Objects.nonNull(roundRequest.getDateStart())) {
			round.setDateStart(roundRequest.getDateStart());
		}
		if(Objects.nonNull(roundRequest.getDateEnd())) {
			round.setDateEnd(roundRequest.getDateEnd());
		}
		return this.roundMapper.roundToRoundResponse(this.roundRepository.saveAndFlush(round));
	}

	public List<MatchSimpleResponse> getMatchesRoundInACompetitionSeason(@NotNull Long competitionId, @ NotNull Long seasonId, @NotNull Integer roundNumber) {
		return this.matchMapper.matchListToMatchSimpleResponseList(
						new ArrayList<>(this.findRoundInACompetitionSeason(competitionId, seasonId, roundNumber).getMatches()));
	}

	public List<MatchSimpleResponse> getMatchesRoundInACompetitionSeasonWithStatus(@NotNull Long competitionId, @ NotNull Long seasonId,
																				   @NotNull Integer roundNumber, @NotNull String matchStatus) {
		return this.matchMapper.matchListToMatchSimpleResponseList(
				this.findRoundInACompetitionSeason(competitionId, seasonId, roundNumber).getMatches().stream()
					.filter(match -> match.getStatus().name().equals(matchStatus)).collect(Collectors.toList()));
	}

	public void generateMatchesAutomatically(Long competitionId, Long seasonId) {
		CompetitionSeason competitionSeason = this.findCompetitionSeason(competitionId, seasonId);
		if (!competitionSeason.getStarted() && competitionSeason.getReturnsNumber().equals(2)
						&& competitionSeason.getTeamsNumber().equals(competitionSeason.getTeamSeasons().size())) {
			// init rounds if necessary
			competitionSeason.setRounds();
			int teamsNumber = competitionSeason.getTeamsNumber();
			List<Round> rounds = competitionSeason.getRounds();
			List<TeamSeason> teamSeasonDisorderedList = shuffleSetTeamSeason(competitionSeason.getTeamSeasons());
			// If number teams odd, add wildcard teamseason
			if (teamSeasonDisorderedList.size() % 2 != 0) {
				teamSeasonDisorderedList.add(new TeamSeason());
			}
			TeamSeason[] teamSeasonsToPair = (TeamSeason[]) teamSeasonDisorderedList.toArray();
			for (int i = 0; i < teamsNumber; i++) {
				Round roundIda = rounds.get(i);
				Round roundReturn = rounds.get(i + (teamsNumber - 1));
				for (int j = 0; j < teamSeasonsToPair.length / 2; j++) {
					TeamSeason firstTeam = teamSeasonsToPair[j];
					TeamSeason secondTeam = teamSeasonsToPair[(teamSeasonsToPair.length - 1) - j];
					if (Objects.nonNull(firstTeam.getId()) && Objects.nonNull(secondTeam.getId())) {
						if (j % 2 == 0) {
							matchRepository.save(generateMatch(firstTeam, secondTeam, roundIda));
							matchRepository.save(generateMatch(secondTeam, firstTeam, roundReturn));
						} else {
							matchRepository.save(generateMatch(secondTeam, firstTeam, roundIda));
							matchRepository.save(generateMatch(firstTeam, secondTeam, roundReturn));
						}
					}
				}
				TeamSeason teamSeasonFirst = teamSeasonsToPair[0];
				for (int position= 0; position < teamSeasonsToPair.length - 2; position++) {
					teamSeasonsToPair[position] = teamSeasonsToPair[position + 1];
				}
				teamSeasonsToPair[teamSeasonsToPair.length - 2] = teamSeasonFirst;
			}
		} else {
			throw new CompetitionSeasonException("Error! The number of teams is incorrect");
		}
	}

	private Match generateMatch(TeamSeason homeTeam, TeamSeason visitantTeam, Round round) {
		Match match = new Match();
		match.setHomeTeam(homeTeam);
		match.setVisitantTeam(visitantTeam);
		match.setRound(round);
		match.setDateMatch(round.getDateStart().toInstant());
		return match;
	}

	private List<TeamSeason> shuffleSetTeamSeason(Set<TeamSeason> teamSeasons) {
		List<TeamSeason> teamSeasonList = new ArrayList<>(teamSeasons);
		Collections.shuffle(new ArrayList<>(teamSeasons));
		return teamSeasonList;
	}



	private CompetitionSeason findCompetitionSeason(@NotNull Long competitionId, @NotNull Long seasonId) {
		Optional<Competition> competition = this.competitionRepository.findById(competitionId);
		Optional<Season> season = this.seasonRepository.findById(seasonId);
		if(competition.isPresent() && season.isPresent()) {
			Optional<CompetitionSeason> competitionSeason = this.competitionSeasonRepository.findByCompetitionAndSeason(competition.get(), season.get());
			if(competitionSeason.isPresent()) {
				return competitionSeason.get();
			}
		}
		throw new CompetitionSeasonException("Error! The competition or the season not exists!");
	}

	private TeamSeason findTeamSeason(@NotNull Long teamId, @NotNull Long seasonId) {
		Optional<Team> team = this.teamRepository.findById(teamId);
		Optional<Season> season = this.seasonRepository.findById(seasonId);
		if(team.isPresent() && season.isPresent()) {
			Optional<TeamSeason> teamSeason = this.teamSeasonRepository.findByTeamAndSeason(team.get(), season.get());
			if(teamSeason.isPresent()) {
				return teamSeason.get();
			} else {
				throw new CompetitionSeasonException("Error! Not exists a team in this season");
			}
		}
		throw new CompetitionSeasonException("Error! The team or the season not exists!");
	}

	private Round findRoundInACompetitionSeason(@NotNull Long competitionId, @NotNull Long seasonId, @NotNull Integer roundNumber) {
		Optional<Round> round = roundRepository.findByCompetitionSeasonAndNumber(
				this.findCompetitionSeason(competitionId, seasonId), roundNumber);
		if (round.isPresent()) {
			return round.get();
		}
		throw new CompetitionSeasonException("Error! The round not exists in this teamSeason");
	}

	private Boolean existsCompetitionSeason(@NotNull Long competitionId, @NotNull Long seasonId) {
		Optional<Competition> competition = this.competitionRepository.findById(competitionId);
		Optional<Season> season = this.seasonRepository.findById(seasonId);
		if(competition.isPresent() && season.isPresent()) {
			Optional<CompetitionSeason> competitionSeason = this.competitionSeasonRepository.findByCompetitionAndSeason(competition.get(), season.get());
			return competitionSeason.isPresent();
		}
		throw new CompetitionSeasonException("Error! The competition or the season not exists!");
	}
}
