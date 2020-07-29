package com.leaguemanagement.leaguemanagement.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Referee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@ManyToMany(mappedBy = "referees")
	private Set<CompetitionSeason> competitionSeasons = new HashSet<>();
	@OneToMany(mappedBy = "referee",cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<Match> matches = new HashSet<>();
	@Column(unique = true, nullable = false, length = 25)
	private String document;
	@Column(nullable = false, length = 50)
	private String name;
	@Column(name = "date_birth")
	private Date dateBirth;
	@Column(length = 30)
	private String nationality;
}
