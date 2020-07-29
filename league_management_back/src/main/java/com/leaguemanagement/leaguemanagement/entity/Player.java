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
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "teamSeasonPlayer")
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	// association with teamseasonplayer
	@OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(value=FetchMode.SELECT)
	private Set<TeamSeasonPlayer> teamSeasonPlayer = new HashSet<>();
	@Column(nullable = false, unique = true, length = 25)
	private String document;
	@Column(nullable = false, length = 50)
	private String name;
	@Column(name = "date_birth")
	private Date dateBirth;
	@Column(nullable = false, length = 30)
	private String nationality;
	@Column(name = "tshirt_size", length = 10)
	private String tshirtSize;
	@Column(name = "short_size", length = 10)
	private String shortSize;
	@Column(name = "boot_size", length = 10)
	private String bootSize;
}
