package com.leaguemanagement.leaguemanagement.entity;

import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.leaguemanagement.commons.leaguebase.dto.enums.MatchStatus;
import com.leaguemanagement.leaguemanagement.exception.MatchException;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Match {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id", nullable = false)
	private Round round;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id", nullable = false)
	private TeamSeason homeTeam;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitant_team_id", nullable = false)
	private TeamSeason visitantTeam;
	@Column(name = "date_match")
	private Instant dateMatch;
	@Column(name = "field_name", length = 50)
	private String fieldName;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referee_id")
	private Referee referee;
	@Enumerated(EnumType.STRING)
	private MatchStatus status = MatchStatus.PENDING;

	public Boolean startMatch() {
		if(this.status.equals(MatchStatus.PENDING)) {
			this.status = MatchStatus.STARTED;
			return true;
		}
		return false;
	}

	public Boolean endMatch() {
		if(this.status.equals(MatchStatus.STARTED)) {
			this.status = MatchStatus.FINISHED;
			return true;
		}
		return false;
	}

	public void setHomeTeam(TeamSeason homeTeam) {
		if(Objects.nonNull(this.visitantTeam) && Objects.nonNull(this.round)) {
			String matchValidation = this.matchValid();
			if (matchValidation.startsWith("Error!")) {
				throw new MatchException(matchValidation);
			}
		}
		this.homeTeam = homeTeam;
	}

	public void setVisitantTeam(TeamSeason visitantTeam) {
		if(Objects.nonNull(this.homeTeam) && Objects.nonNull(this.round)) {
			String matchValidation = this.matchValid();
			if (matchValidation.startsWith("Error!")) {
				throw new MatchException(matchValidation);
			}
		}
		this.visitantTeam = visitantTeam;
	}

	public void setRound(Round round) {
		if(Objects.nonNull(this.homeTeam) && Objects.nonNull(this.visitantTeam)) {
			String matchValidation = this.matchValid();
			if (matchValidation.startsWith("Error!")) {
				throw new MatchException(matchValidation);
			}
		}
		this.round = round;
	}

	public void setReferee(Referee referee) throws MatchException {
		if(Objects.nonNull(this.round) && referee.getCompetitionSeasons().stream()
				.anyMatch(competitionSeason -> competitionSeason.equals(this.round.getCompetitionSeason()))) {
			this.referee = referee;
		}
		throw new MatchException("The round should be set before the referee - the round's season and the referee's season should be the same");
	}

	public String matchValid() {
		if(Objects.nonNull(this.getHomeTeam()) && Objects.nonNull(this.getVisitantTeam())
						&& Objects.nonNull(this.getRound())) {
			if (this.homeTeam.equals(this.visitantTeam)) {
				return "Error! The homeTeam and visitantTeam can't be the same";
			}
			if (this.homeTeam.getCompetitionSeasons().stream().noneMatch(competitionSeason -> competitionSeason
					.equals(this.round.getCompetitionSeason()))) {
				return "Error! The homeTeam's season and the round's season should be the same";
			}
			if (this.visitantTeam.getCompetitionSeasons().stream().noneMatch(competitionSeason -> competitionSeason
					.equals(this.round.getCompetitionSeason()))) {
				return "Error! The visitantTeam's season and the round's season should be the same";
			}
			if (this.round.containsTeamInMatch(this.getHomeTeam(), this.getVisitantTeam())) {
				return "Error! The homeTeam and the visitantTeam already play in this round";
			}
			if(this.round.getCompetitionSeason().matchUpNotRepeatable(this.getHomeTeam(), this.getVisitantTeam())) {
				return "Error! The homeTeam and the visitantTeam already play the total rounds fixed in competition";
			}
			return "Match valid!";
		}
		throw new MatchException("Error! homeTeam, visitantTeam and round are mandatories!");
	}
}
