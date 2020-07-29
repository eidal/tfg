package com.leaguemanagement.leaguemanagement.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import com.leaguemanagement.commons.leaguebase.dto.request.TeamSeasonPlayerRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.TeamSeasonPlayerResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.TeamSeasonPlayerSimpleResponse;
import com.leaguemanagement.leaguemanagement.entity.Player;
import com.leaguemanagement.leaguemanagement.entity.Season;
import com.leaguemanagement.leaguemanagement.entity.Team;
import com.leaguemanagement.leaguemanagement.entity.TeamSeason;
import com.leaguemanagement.leaguemanagement.entity.TeamSeasonPlayer;
import com.leaguemanagement.leaguemanagement.exception.PlayerException;
import com.leaguemanagement.leaguemanagement.exception.TeamSeasonException;
import com.leaguemanagement.leaguemanagement.exception.TeamSeasonPlayerException;
import com.leaguemanagement.leaguemanagement.mapper.TeamSeasonPlayerMapper;
import com.leaguemanagement.leaguemanagement.repository.PlayerRepository;
import com.leaguemanagement.leaguemanagement.repository.SeasonRepository;
import com.leaguemanagement.leaguemanagement.repository.TeamRepository;
import com.leaguemanagement.leaguemanagement.repository.TeamSeasonPlayerRepository;
import com.leaguemanagement.leaguemanagement.repository.TeamSeasonRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class TeamSeasonPlayerService {

	private final TeamRepository teamRepository;
	private final SeasonRepository seasonRepository;
	private final PlayerRepository playerRepository;
	private final TeamSeasonRepository teamSeasonRepository;
	private final TeamSeasonPlayerRepository teamSeasonPlayerRepository;
	private final TeamSeasonPlayerMapper teamSeasonPlayerMapper;

	public TeamSeasonPlayerService(final TeamRepository teamRepository, final SeasonRepository seasonRepository,
			final PlayerRepository playerRepository, final TeamSeasonRepository teamSeasonRepository,
			final TeamSeasonPlayerRepository teamSeasonPlayerRepository, TeamSeasonPlayerMapper teamSeasonPlayerMapper) {
		this.teamRepository = teamRepository;
		this.seasonRepository = seasonRepository;
		this.playerRepository = playerRepository;
		this.teamSeasonRepository = teamSeasonRepository;
		this.teamSeasonPlayerRepository = teamSeasonPlayerRepository;
		this.teamSeasonPlayerMapper = teamSeasonPlayerMapper;
	}

	public TeamSeasonPlayerResponse getTeamSeasonPlayerById(@NotNull Long teamSeasonPlayerId) {
		 return this.teamSeasonPlayerMapper.teamSeasonPlayerToTeamSeasonPlayerResponse(
				 this.findTeamSeasonPlayerById(teamSeasonPlayerId));
	}

	public TeamSeasonPlayerResponse getTeamSeasonPlayer(@NotNull Long teamId, @NotNull Long seasonId, @NotNull Long playerId) {
		 return this.teamSeasonPlayerMapper.teamSeasonPlayerToTeamSeasonPlayerResponse(
				 this.findTeamSeasonPlayer(teamId, seasonId, playerId));
	}

	public TeamSeasonPlayerResponse createTeamSeasonPlayer(@NotNull Long teamId, @NotNull Long seasonId, @NotNull Long playerId,
			@Valid TeamSeasonPlayerRequest teamSeasonPlayerRequest) {
		if(!this.existsTeamSeasonPlayer(teamId, seasonId, playerId)) {
			TeamSeason teamSeason = this.findTeamSeason(teamId, seasonId);
			Optional<Player> playerOptional = this.playerRepository.findById(playerId);
			TeamSeasonPlayer teamSeasonPlayer = new TeamSeasonPlayer();
			Player player = Optional.ofNullable(playerOptional)
							.map(Optional::get)
							.orElseThrow(() -> new PlayerException("Error! The player not exists"));
			teamSeasonPlayer.setPlayer(player);
			teamSeasonPlayer.setTeamSeason(teamSeason);
			if(Objects.nonNull(teamSeasonPlayerRequest)) {
				teamSeasonPlayer.setActive(Objects.nonNull(teamSeasonPlayerRequest.getActive()) ? teamSeasonPlayerRequest.getActive() : false);
				if(Objects.nonNull(teamSeasonPlayerRequest.getDorsalNumber())) {
					teamSeasonPlayer.setDorsalNumber(teamSeasonPlayerRequest.getDorsalNumber());
				}
			} else {
				teamSeasonPlayer.setActive(false);
			}
			this.checkTeamSeasonPlayerValid(teamSeasonPlayer, null);

			return this.teamSeasonPlayerMapper.teamSeasonPlayerToTeamSeasonPlayerResponse(
					this.teamSeasonPlayerRepository.save(teamSeasonPlayer));
		}
		throw new TeamSeasonPlayerException("Error! The player already exists in the teamSeason specified");
	}

	public TeamSeasonPlayerResponse updateTeamSeasonPlayer(@NotNull Long teamId, @NotNull Long seasonId, @NotNull Long playerId,
			@NotNull TeamSeasonPlayerRequest teamSeasonPlayerRequest) {
		TeamSeasonPlayer teamSeasonPlayer = this.findTeamSeasonPlayer(teamId, seasonId, playerId);
		// TODO : Improve this logic
		if(Objects.nonNull(teamSeasonPlayerRequest.getActive())) {
			teamSeasonPlayer.setActive(teamSeasonPlayerRequest.getActive());
		}
		if (teamSeasonPlayer.getActive()) {
			this.checkTeamSeasonPlayerValid(teamSeasonPlayer,
					Objects.nonNull(teamSeasonPlayerRequest.getDorsalNumber()) ? teamSeasonPlayerRequest.getDorsalNumber() : null);
		}
		if(Objects.nonNull(teamSeasonPlayerRequest.getDorsalNumber())) {
			teamSeasonPlayer.setDorsalNumber(teamSeasonPlayerRequest.getDorsalNumber());
		}

		TeamSeasonPlayer teamSeasonPlayerAux = teamSeasonPlayerRepository.saveAndFlush(teamSeasonPlayer);
		return this.teamSeasonPlayerMapper.teamSeasonPlayerToTeamSeasonPlayerResponse(teamSeasonPlayerAux);
	}

	public TeamSeasonPlayerResponse updateTeamSeasonPlayerById(@NotNull Long teamSeasonPlayerId,
			@NotNull TeamSeasonPlayerRequest teamSeasonPlayerRequest) {
		TeamSeasonPlayer teamSeasonPlayer = this.findTeamSeasonPlayerById(teamSeasonPlayerId);
		// TODO : Improve this logic
		if(Objects.nonNull(teamSeasonPlayerRequest.getActive())) {
			teamSeasonPlayer.setActive(teamSeasonPlayerRequest.getActive());
		}
		if (teamSeasonPlayer.getActive()) {
			this.checkTeamSeasonPlayerValid(teamSeasonPlayer,
					Objects.nonNull(teamSeasonPlayerRequest.getDorsalNumber()) ? teamSeasonPlayerRequest.getDorsalNumber() : null);
		}
		if(Objects.nonNull(teamSeasonPlayerRequest.getDorsalNumber())) {
			teamSeasonPlayer.setDorsalNumber(teamSeasonPlayerRequest.getDorsalNumber());
		}

		TeamSeasonPlayer teamSeasonPlayerAux = teamSeasonPlayerRepository.saveAndFlush(teamSeasonPlayer);
		return this.teamSeasonPlayerMapper.teamSeasonPlayerToTeamSeasonPlayerResponse(teamSeasonPlayerAux);
	}

	public List<TeamSeasonPlayerSimpleResponse> getAllTeamSeasonPlayerByTeamSeason(@NotNull Long teamId, @NotNull Long seasonId) {
		return this.teamSeasonPlayerMapper.teamSeasonPlayerListToTeamSeasonPlayerSimpleResponseList(
				this.teamSeasonPlayerRepository.findByTeamSeason(this.findTeamSeason(teamId, seasonId)));
	}

	private TeamSeason findTeamSeason(@NotNull Long teamId, @NotNull Long seasonId) {
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

	private TeamSeasonPlayer findTeamSeasonPlayer(@NotNull Long teamId, @NotNull Long seasonId, @NotNull Long playerId) {
		Optional<Team> team = this.teamRepository.findById(teamId);
		Optional<Season> season = this.seasonRepository.findById(seasonId);
		Optional<Player> player = this.playerRepository.findById(playerId);
		if(team.isPresent() && season.isPresent() && player.isPresent()) {
			Optional<TeamSeason> teamSeason = this.teamSeasonRepository.findByTeamAndSeason(team.get(), season.get());
			if(teamSeason.isPresent()) {
				Optional<TeamSeasonPlayer> teamSeasonPlayer =
						this.teamSeasonPlayerRepository.findByTeamSeasonAndPlayer(teamSeason.get(), player.get());
				if(teamSeasonPlayer.isPresent()) {
					return teamSeasonPlayer.get();
				}
				throw new TeamSeasonPlayerException("Error! The player is not vinculated with the teamSeason specified");
			}
		}
		throw new TeamSeasonPlayerException("Error! The team or the season or the player not exists!");
	}

	private TeamSeasonPlayer findTeamSeasonPlayerById(@NotNull Long teamSeasonPlayerId) {
		Optional<TeamSeasonPlayer> teamSeasonPlayer = this.teamSeasonPlayerRepository.findById(teamSeasonPlayerId);
		return Optional.ofNullable(teamSeasonPlayer)
					.map(Optional::get)
				.orElseThrow(() -> new TeamSeasonPlayerException("Error! The teamSeasonPlayer not exists!"));
	}

	private Boolean existsTeamSeasonPlayer(@NotNull Long teamId, @NotNull Long seasonId, @NotNull Long playerId) {
		Optional<Team> team = this.teamRepository.findById(teamId);
		Optional<Season> season = this.seasonRepository.findById(seasonId);
		Optional<Player> player = this.playerRepository.findById(playerId);
		if(team.isPresent() && season.isPresent() && player.isPresent()) {
			Optional<TeamSeason> teamSeason = this.teamSeasonRepository.findByTeamAndSeason(team.get(), season.get());
			return (teamSeason.isPresent() &&
					this.teamSeasonPlayerRepository.findByTeamSeasonAndPlayer(teamSeason.get(), player.get())
						.isPresent());
		}
		throw new TeamSeasonPlayerException("Error! The team or the season or the player not exists!");
	}

	private Boolean dorsalNumberIsNotAvailable(@NotNull TeamSeason teamSeason, @NotNull Integer dorsalNumber) {
		return !this.teamSeasonPlayerRepository.findByTeamSeasonAndDorsalNumberAndActive(teamSeason,
				dorsalNumber, true).isEmpty();
	}

	private void checkTeamSeasonPlayerValid(@NotNull TeamSeasonPlayer teamSeasonPlayer, Integer dorsalNumber) {
		if(Objects.nonNull(teamSeasonPlayer.getActive()) && teamSeasonPlayer.getActive()) {
			if(Objects.nonNull(dorsalNumber) || Objects.nonNull(teamSeasonPlayer.getDorsalNumber())) {
				if(this.dorsalNumberIsNotAvailable(teamSeasonPlayer.getTeamSeason(),
						Objects.nonNull(dorsalNumber) ? dorsalNumber : teamSeasonPlayer.getDorsalNumber())) {
					throw new TeamSeasonPlayerException("Error! The dorsal number is not available");
				}
			} else {
				throw new TeamSeasonPlayerException("Error! An active player must have a dorsal number");
			}
		}
	}
}
