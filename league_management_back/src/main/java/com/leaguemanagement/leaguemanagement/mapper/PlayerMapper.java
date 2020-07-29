package com.leaguemanagement.leaguemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.leaguemanagement.commons.leaguebase.dto.request.PlayerRequest;
import com.leaguemanagement.commons.leaguebase.dto.response.PlayerResponse;
import com.leaguemanagement.leaguemanagement.entity.Player;

@Mapper
public interface PlayerMapper {

	PlayerResponse playerToPlayerResponse(Player player);

	Player playerRequestToPlayer(PlayerRequest playerRequest);

	List<PlayerResponse> playerListToPlayerResponseList(List<Player> playerList);
}
