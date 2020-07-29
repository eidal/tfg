package com.leaguemanagement.leaguemanagement.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.leaguemanagement.commons.leaguebase.dto.request.SeasonRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.SeasonResponse;
import com.leaguemanagement.leaguemanagement.entity.Season;
import com.leaguemanagement.leaguemanagement.exception.SeasonException;
import com.leaguemanagement.leaguemanagement.mapper.SeasonMapper;
import com.leaguemanagement.leaguemanagement.repository.SeasonRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SeasonService {

	private final SeasonRepository seasonRepository;

	private final SeasonMapper seasonMapper;

	public SeasonService(SeasonRepository seasonRepository, SeasonMapper seasonMapper) {
		this.seasonRepository = seasonRepository;
		this.seasonMapper = seasonMapper;
	}

	public SeasonResponse getSeasonById(@NotNull Long seasonId) {
		Season season = this.getSeason(seasonId);

		return this.seasonMapper.seasonToSeasonResponse(season);
	}

	public SeasonResponse getSeasonByYearStart(@NotNull Integer yearStart) {
		Season season = this.seasonRepository.findByYearStart(yearStart);

		if(Objects.nonNull(season)) {
			return this.seasonMapper.seasonToSeasonResponse(season);
		} else {
			SeasonService.log.error("Error! The season with year start {} not exists", yearStart);
			throw new SeasonException("Error! The season not exists");
		}
	}

	public SeasonResponse createSeason(@Valid SeasonRequest seasonRequest) {
		Season season = this.seasonRepository.save(
				this.seasonMapper.seasonRequestToSeason(seasonRequest));

		return this.seasonMapper.seasonToSeasonResponse(season);
	}

	public List<SeasonResponse> getAllSeasons() {
		return this.seasonMapper.seasonListToSeasonResponseList(
				this.seasonRepository.findAll());
	}

	public String deleteSeasonById(@NotNull Long seasonId) {
		try {
			this.seasonRepository.deleteById(seasonId);
		} catch (EmptyResultDataAccessException e) {
			SeasonService.log.error("Error! The season with id {} not exists", seasonId);
			throw new SeasonException("Error! The season not exists", e);
		}
		return "The season was deleted correctly!";
	}

	public SeasonResponse updateSeasonById(@NotNull Long seasonId, @NotNull Map<String, Object> seasonParameters) {
		Season season = this.getSeason(seasonId);
		try {
			BeanUtils.populate(season, seasonParameters);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Error! The update fail, try again later!");
		}

		Season seasonaux=seasonRepository.saveAndFlush(season);

		return this.seasonMapper.seasonToSeasonResponse(seasonaux);
	}

	private Season getSeason(@NotNull Long seasonId) {
		Optional<Season> season = this.seasonRepository.findById(seasonId);
		if(season.isPresent()) {
			return season.get();
		} else {
			SeasonService.log.error("Error! The season with id {} not exists", seasonId);
			throw new SeasonException("Error! The season not exists");
		}
	}

}
