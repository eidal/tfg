package com.leaguemanagement.leaguemanagement.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.leaguemanagement.leaguemanagement.exception.CompetitionSeasonException;

import lombok.Data;

@Entity
@Data
public class CompetitionSeason {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	// relation with a competition
	@ManyToOne
	@JoinColumn(name = "competition_id")
	private Competition competition;
	// relation with a season
	@ManyToOne
	@JoinColumn(name = "season_id")
	private Season season;
	@JoinTable(
	        name = "competition_season_referee",
	        joinColumns = @JoinColumn(name = "FK_Competition_Season", nullable = false),
	        inverseJoinColumns = @JoinColumn(name="FK_Referee", nullable = false)
	    )
	// relation with many referees
	@ManyToMany
	private Set<Referee> referees = new HashSet<>();
	@JoinTable(
	        name = "competition_season_team_season",
	        joinColumns = @JoinColumn(name = "FK_Competition_Season", nullable = false),
	        inverseJoinColumns = @JoinColumn(name="FK_Team_Season", nullable = false)
	    )
	// relation with many teamSeasons
	@ManyToMany
	private Set<TeamSeason> teamSeasons = new HashSet<>();
	// relation with many rounds
	@OneToMany(mappedBy = "competitionSeason",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Round> rounds = new ArrayList<>();
	@Column(name = "teams_number", nullable = false)
	private Integer teamsNumber;
	@Column(name = "returns_number", nullable = false)
	private Integer returnsNumber;
	@Column(name = "points_win", nullable = false)
	private Integer pointsWin;
	@Column(name = "points_tie", nullable = false)
	private Integer pointsTie;
	@Column(columnDefinition = "boolean default false")
	private Boolean started;

	public CompetitionSeason() {
		this.started = false;
	}

	public void addReferee(@NotNull Referee referee) {
		referees.add(referee);
	}

	public void addTeamSeason(@NotNull TeamSeason teamSeason) {
		if (teamsNumber > teamSeasons.size()) {
			if (teamSeason.getSeason().getId().equals(this.season.getId())) {
				teamSeasons.add(teamSeason);
			} else {
				throw new CompetitionSeasonException("Error! The team's season must be the same that the competition's season!");
			}
		} else {
			throw new CompetitionSeasonException("Error! The maximum number of teams has been exceeded!");
		}
	}

	public void setRounds() {
		if (!this.started && Objects.nonNull(teamsNumber) && Objects.nonNull(returnsNumber)) {
			int numberRounds = (teamsNumber-1) * returnsNumber;
			if (numberRounds != rounds.size()) {
				rounds.clear();
				for (int round = 1; round <= numberRounds; round++) {
					Round roundObject = new Round();
					roundObject.setNumber(round);
					rounds.add(roundObject);
				}
			}
		}
	}

	public Boolean numberRoundsCorrect() {
		return this.rounds.size() == (teamsNumber-1) * returnsNumber;
	}

	public void setRounds(Set<Round> rounds) {
		//do nothing
	}

	public Boolean matchUpNotRepeatable(TeamSeason homeTeam, TeamSeason visitantTeam) {
		return (this.rounds.parallelStream().filter(round ->
						round.containsMatchUp(homeTeam, visitantTeam)).count() + 1) < this.returnsNumber;
	}
}
