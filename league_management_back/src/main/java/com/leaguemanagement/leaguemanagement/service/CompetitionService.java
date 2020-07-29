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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.leaguemanagement.commons.leaguebase.dto.request.CompetitionRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.CompetitionResponse;
import com.leaguemanagement.leaguemanagement.entity.Competition;
import com.leaguemanagement.leaguemanagement.exception.CompetitionException;
import com.leaguemanagement.leaguemanagement.mapper.CompetitionMapper;
import com.leaguemanagement.leaguemanagement.repository.CompetitionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompetitionService {

	private final CompetitionRepository competitionRepository;

	private final CompetitionMapper competitionMapper;

	public CompetitionService(CompetitionRepository competitionRepository, CompetitionMapper competitionMapper) {
		this.competitionRepository = competitionRepository;
		this.competitionMapper = competitionMapper;
	}

	public CompetitionResponse getCompetitionById(@NotNull Long competitionId) {
		Competition competition = this.getCompetition(competitionId);

		return this.competitionMapper.competitionToCompetitionResponse(competition);
	}

	public List<CompetitionResponse> getCompetitionByName(@NotEmpty String name) {
		return this.competitionMapper.competitionListToCompetitionResponseList(this.competitionRepository.findByNameContainingIgnoreCase(name));
	}

	public CompetitionResponse createCompetition(@Valid CompetitionRequest competitionRequest) {
		try {
			Competition competition = this.competitionRepository.save(
					this.competitionMapper.competitionRequestToCompetition(competitionRequest));

			return this.competitionMapper.competitionToCompetitionResponse(competition);
		} catch (ConstraintViolationException e) {
			CompetitionService.log.error(e.getMessage());
			throw new CompetitionException("Error! Competition's document already exists!", e);
		}
	}

	public List<CompetitionResponse> getAllCompetitions() {
		return this.competitionMapper.competitionListToCompetitionResponseList(
				this.competitionRepository.findAll());
	}

	public String deleteCompetitionById(@NotNull Long competitionId) {
		try {
			this.competitionRepository.deleteById(competitionId);
		} catch (EmptyResultDataAccessException e) {
			CompetitionService.log.error("Error! The competition with id {} not exists", competitionId);
			throw new CompetitionException("Error! The competition not exists", e);
		}
		return "The competition was deleted correctly!";
	}

	public CompetitionResponse updateCompetitionById(@NotNull Long competitionId, @NotNull Map<String, Object> competitionParameters) {
		Competition competition = this.getCompetition(competitionId);
		try {
			BeanUtils.populate(competition, competitionParameters);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Error! The update fail, try again later!");
		}
		try {
			Competition competitionaux=competitionRepository.saveAndFlush(competition);
			return this.competitionMapper.competitionToCompetitionResponse(competitionaux);
		} catch (ConstraintViolationException e) {
			CompetitionService.log.error(e.getMessage());
			throw new CompetitionException("Error! Competition's document already exists!", e);
		}
	}

	private Competition getCompetition(@NotNull Long competitionId) {
		Optional<Competition> competition = this.competitionRepository.findById(competitionId);
		if(competition.isPresent()) {
			return competition.get();
		} else {
			CompetitionService.log.error("Error! The competition with id {} not exists", competitionId);
			throw new CompetitionException("Error! The competition not exists");
		}
	}

}
