package com.leaguemanagement.leaguemanagement.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.leaguemanagement.commons.leaguebase.dto.request.RefereeRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.MatchSimpleResponse;
import com.leaguemanagement.commons.leaguebase.dto.response.RefereeResponse;
import com.leaguemanagement.leaguemanagement.entity.Referee;
import com.leaguemanagement.leaguemanagement.exception.RefereeException;
import com.leaguemanagement.leaguemanagement.mapper.MatchMapper;
import com.leaguemanagement.leaguemanagement.mapper.RefereeMapper;
import com.leaguemanagement.leaguemanagement.repository.RefereeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RefereeService {

	private final RefereeRepository refereeRepository;

	private final RefereeMapper refereeMapper;

	private final MatchMapper matchMapper;

	public RefereeService(final RefereeRepository refereeRepository, final RefereeMapper refereeMapper,
			final MatchMapper matchMapper) {
		this.refereeRepository = refereeRepository;
		this.refereeMapper = refereeMapper;
		this.matchMapper = matchMapper;
	}

	public RefereeResponse getRefereeById(@NotNull Long refereeId) {
		Referee referee = this.getReferee(refereeId);

		return this.refereeMapper.refereeToRefereeResponse(referee);
	}

	public List<RefereeResponse> getRefereeByName(@NotEmpty String name) {
		return this.refereeMapper.refereeListToRefereeResponseList(this.refereeRepository.findByNameContainingIgnoreCase(name));
	}

	public RefereeResponse getRefereeByDocument(String document) {
		Referee referee = this.refereeRepository.findByDocumentIgnoreCase(document);

		if(Objects.nonNull(referee)) {
			return this.refereeMapper.refereeToRefereeResponse(referee);
		} else {
			RefereeService.log.error("Error! The referee with document {} not exists", document);
			throw new RefereeException("Error! The referee not exists");
		}
	}

	public RefereeResponse createReferee(@Valid RefereeRequest refereeRequest) {
		try {
			Referee referee = this.refereeRepository.save(
					this.refereeMapper.refereeRequestToReferee(refereeRequest));

			return this.refereeMapper.refereeToRefereeResponse(referee);
		} catch (ConstraintViolationException e) {
			RefereeService.log.error(e.getMessage());
			throw new RefereeException("Error! Referee's document already exists!", e);
		}
	}

	public List<RefereeResponse> getAllReferees() {
		return this.refereeMapper.refereeListToRefereeResponseList(
				this.refereeRepository.findAll());
	}

	public String deleteRefereeById(@NotNull Long refereeId) {
		try {
			this.refereeRepository.deleteById(refereeId);
		} catch (EmptyResultDataAccessException e) {
			RefereeService.log.error("Error! The referee with id {} not exists", refereeId);
			throw new RefereeException("Error! The referee not exists", e);
		}
		return "The referee was deleted correctly!";
	}

	public RefereeResponse updateRefereeById(@NotNull Long refereeId, @NotNull Map<String, Object> refereeParameters) {
		Referee referee = this.getReferee(refereeId);
		try {
			BeanUtils.populate(referee, refereeParameters);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Error! The update fail, try again later!");
		}
		try {
			Referee refereeaux=refereeRepository.saveAndFlush(referee);
			return this.refereeMapper.refereeToRefereeResponse(refereeaux);
		} catch (ConstraintViolationException e) {
			RefereeService.log.error(e.getMessage());
			throw new RefereeException("Error! Referee's document already exists!", e);
		}
	}

	public List<MatchSimpleResponse> getAllMatches(@NotNull Long refereeId) {
		return this.matchMapper.matchListToMatchSimpleResponseList(
						new ArrayList<>(this.getReferee(refereeId).getMatches()));
	}

	public List<MatchSimpleResponse> getAllMatchesByStatus(@NotNull Long refereeId, @NotNull String matchStatus) {
		return this.matchMapper.matchListToMatchSimpleResponseList(
				this.getReferee(refereeId).getMatches().stream().
				filter(match -> match.getStatus().name().equals(matchStatus)).collect(Collectors.toList()));
	}

	private Referee getReferee(@NotNull Long refereeId) {
		Optional<Referee> referee = this.refereeRepository.findById(refereeId);
		if(referee.isPresent()) {
			return referee.get();
		} else {
			RefereeService.log.error("Error! The referee with id {} not exists", refereeId);
			throw new RefereeException("Error! The referee not exists");
		}
	}

}
