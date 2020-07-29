package com.leaguemanagement.leaguemanagement.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

import com.leaguemanagement.commons.leaguebase.dto.request.PlayerRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.PlayerResponse;
import com.leaguemanagement.leaguemanagement.entity.Player;
import com.leaguemanagement.leaguemanagement.exception.PlayerException;
import com.leaguemanagement.leaguemanagement.mapper.PlayerMapper;
import com.leaguemanagement.leaguemanagement.repository.PlayerRepository;
import com.leaguemanagement.leaguemanagement.utils.LeagueManagementConsts;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@CacheConfig(cacheNames = LeagueManagementConsts.PLAYER_CACHE_NAME, cacheManager = "expireOneDay")
public class PlayerService {

	private final PlayerRepository playerRepository;

	private final PlayerMapper playerMapper;

	private final CacheManager expireOneDayCache;

	public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper,
						 CacheManager cacheManager) {
		this.playerRepository = playerRepository;
		this.playerMapper = playerMapper;
		this.expireOneDayCache = cacheManager;
	}

	@Cacheable(cacheNames = LeagueManagementConsts.PLAYER_CACHE_NAME, cacheManager = "expireOneDay",
					key = "{ #root.methodName: #playerId }")
	public PlayerResponse getPlayerById(@NotNull Long playerId) {
		Player player = this.getPlayer(playerId);

		return this.playerMapper.playerToPlayerResponse(player);
	}

	@Cacheable(cacheNames = LeagueManagementConsts.PLAYER_CACHE_NAME, cacheManager = "expireOneDay",
					key = "{ #root.methodName: #name }")
	public List<PlayerResponse> getPlayerByName(@NotEmpty String name) {
		return this.playerMapper.playerListToPlayerResponseList(this.playerRepository.findByNameContainingIgnoreCase(name));
	}

	@Cacheable(cacheNames = LeagueManagementConsts.PLAYER_CACHE_NAME, cacheManager = "expireOneDay",
					key = "{ #root.methodName: #nationality }")
	public List<PlayerResponse> getPlayerByNationality(@NotEmpty String nationality) {
		return this.playerMapper.playerListToPlayerResponseList(this.playerRepository.findByNationalityIgnoreCase(nationality));
	}

	@Cacheable(cacheNames = LeagueManagementConsts.PLAYER_CACHE_NAME, cacheManager = "expireOneDay",
					key = "{ #root.methodName: #document }")
	public PlayerResponse getPlayerByDocument(String document) {
		Player player = this.playerRepository.findByDocumentIgnoreCase(document);

		if(Objects.nonNull(player)) {
			return this.playerMapper.playerToPlayerResponse(player);
		} else {
			PlayerService.log.error("Error! The player with document {} not exists", document);
			throw new PlayerException("Error! The player not exists");
		}
	}

	public PlayerResponse createPlayer(@Valid PlayerRequest playerRequest) {
		try {
			Player player = this.playerRepository.save(
					this.playerMapper.playerRequestToPlayer(playerRequest));
			this.clearPlayerCache(player);
			return this.playerMapper.playerToPlayerResponse(player);
		} catch (ConstraintViolationException e) {
			PlayerService.log.error(e.getMessage());
			throw new PlayerException("Error! Player's document already exists!", e);
		}
	}

	@Cacheable(cacheNames = LeagueManagementConsts.PLAYER_CACHE_NAME, cacheManager = "expireOneDay",
					key = "{ #root.methodName }")
	public List<PlayerResponse> getAllPlayers() {
		return this.playerMapper.playerListToPlayerResponseList(
				this.playerRepository.findAll());
	}


	public String deletePlayerById(@NotNull Long playerId) {
		Optional<Player> player = this.playerRepository.findById(playerId);
		try {
			this.playerRepository.deleteById(playerId);
		} catch (EmptyResultDataAccessException e) {
			PlayerService.log.error("Error! The player with id {} not exists", playerId);
			throw new PlayerException("Error! The player not exists", e);
		}
		player.ifPresent(this::clearPlayerCache);
		return "The player was deleted correctly!";
	}

	public PlayerResponse updatePlayerById(@NotNull Long playerId, @NotNull Map<String, Object> playerParameters) {
		Player player = this.getPlayer(playerId);
		this.clearPlayerCache(player);
		try {
			BeanUtils.populate(player, playerParameters);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Error! The update fail, try again later!");
		}

		try {
			Player playeraux=playerRepository.saveAndFlush(player);
			this.clearPlayerCache(playeraux);
			return this.playerMapper.playerToPlayerResponse(playeraux);
		} catch (ConstraintViolationException e) {
			PlayerService.log.error(e.getMessage());
			throw new PlayerException("Error! Player's document already exists!", e);
		}

	}

	private Player getPlayer(@NotNull Long playerId) {
		Optional<Player> player = this.playerRepository.findById(playerId);
		if(player.isPresent()) {
			return player.get();
		} else {
			PlayerService.log.error("Error! The player with id {} not exists", playerId);
			throw new PlayerException("Error! The player not exists");
		}
	}

	private void clearPlayerCache(Player currentPlayer) {
		Optional.ofNullable(expireOneDayCache.getCache(LeagueManagementConsts.PLAYER_CACHE_NAME))
						.ifPresent(cache -> {
							cache.evict("getPlayerByName:" + currentPlayer.getName());
							cache.evict("getPlayerByNationality:" + currentPlayer.getNationality());
							cache.evict("getAllPlayers");
							cache.evict("getPlayerByDocument:" + currentPlayer.getDocument());
							cache.evict("getPlayerById:" + currentPlayer.getId());
						});
	}

}
