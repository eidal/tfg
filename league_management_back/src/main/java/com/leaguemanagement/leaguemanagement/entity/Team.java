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
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	// association with teamseason
	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(value=FetchMode.SELECT)
	private Set<TeamSeason> teamSeason = new HashSet<>();
	@Column(nullable = false, length = 50)
	private String name;
	@Column(length = 200)
	private String shield;
	
}
