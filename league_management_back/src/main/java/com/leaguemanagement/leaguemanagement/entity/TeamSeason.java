package com.leaguemanagement.leaguemanagement.entity;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "teamSeasonPlayer")
public class TeamSeason {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	// association with teamseasonplayer
	@OneToMany(mappedBy = "teamSeason", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(value=FetchMode.SELECT)
	private Set<TeamSeasonPlayer> teamSeasonPlayer = new HashSet<>();
	@ManyToMany(mappedBy = "teamSeasons")
	private Set<CompetitionSeason> competitionSeasons = new HashSet<>();
	// association with match
	@OneToMany(mappedBy = "homeTeam",cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<Match> homeMatch = new HashSet<>();
	@OneToMany(mappedBy = "visitantTeam",cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<Match> visitantMatch = new HashSet<>();
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;
	@ManyToOne
	@JoinColumn(name = "season_id")
	private Season season;
	@Column(name = "president_name", length = 50)
	private String presidentName;
	@Column(name = "coach_name", nullable = false, length = 50)
	private String coachName;
	@Column(name = "first_equipment_description", length = 50)
	private String firstEquipmentDescription;
	@Column(name = "second_equipment_description", length = 50)
	private String secondEquipmentDescription;
}
