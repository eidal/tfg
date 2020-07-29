package com.leaguemanagement.leaguemanagement.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Round {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@OneToMany(mappedBy = "round",cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<Match> matches = new HashSet<>();
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_season_id", nullable = false)
	private CompetitionSeason competitionSeason;
	@Column(name = "number", nullable = false)
	private Integer number;
	@Column(name = "description")
	private String description;
	@Column(name="date_start")
	private Date dateStart;
	@Column(name="date_end")
	private Date dateEnd;

	public Boolean containsTeamInMatch(@NotNull TeamSeason teamHome, @NotNull TeamSeason teamVisitant) {
		return this.matches.stream().anyMatch(match ->
						teamHome.equals(match.getHomeTeam()) || teamHome.equals(match.getVisitantTeam())
							|| teamVisitant.equals(match.getHomeTeam()) || teamVisitant.equals(match.getVisitantTeam()));
	}

	public Boolean containsMatchUp(@NotNull TeamSeason teamHome, @NotNull TeamSeason teamVisitant) {
		return this.matches.stream().anyMatch(match ->
		(teamHome.equals(match.getHomeTeam()) && teamVisitant.equals(match.getVisitantTeam()))
		|| (teamVisitant.equals(match.getHomeTeam()) && teamHome.equals(match.getVisitantTeam())));
	}
}
