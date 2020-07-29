package com.leaguemanagement.leaguemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leaguemanagement.leaguemanagement.entity.Season;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
	Season findByYearStart(Integer yearStart);
}
