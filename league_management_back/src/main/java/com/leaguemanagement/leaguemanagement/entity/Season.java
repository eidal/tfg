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
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "teamSeason")
public class Season {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	// association with teamseason
	@OneToMany(mappedBy = "season", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(value=FetchMode.SELECT)
	private Set<TeamSeason> teamSeason = new HashSet<>();
	// association with competitionseason
	@OneToMany(mappedBy = "season", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(value=FetchMode.SELECT)
	private Set<CompetitionSeason> competitionSeason = new HashSet<>();
	@Column(name = "year_start", nullable = false)
	private Integer yearStart;
	@Column(name = "year_end", nullable = false)
	private Integer yearEnd;
}
