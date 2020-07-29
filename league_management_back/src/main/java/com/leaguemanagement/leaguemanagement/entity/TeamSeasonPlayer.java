package com.leaguemanagement.leaguemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class TeamSeasonPlayer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "teamseason_id")
	private TeamSeason teamSeason;
	@ManyToOne
	@JoinColumn(name = "player_id")
	private Player player;
	private Integer dorsalNumber;
	@Column(columnDefinition = "boolean default false")
	private Boolean active;
}
