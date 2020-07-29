package com.leaguemanagement.leaguemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leaguemanagement.leaguemanagement.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
	Player findByDocumentIgnoreCase(String document);
	List<Player> findByNameContainingIgnoreCase(String name);
	List<Player> findByNationalityIgnoreCase(String nationality);
}
