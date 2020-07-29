package com.leaguemanagement.commons.leaguebase.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class TeamSeasonPlayerResponse implements Serializable {
	@ApiModelProperty(notes = "TeamSeasonPlayer's identification", example = "0", position = 0)
	Long id;
	@ApiModelProperty(notes = "Team's identification", example = "0", position = 1)
	Long teamId;
	@ApiModelProperty(notes = "Team's name", example = "Atlético de Madrid", position = 2)
	String teamName;
	@ApiModelProperty(notes = "Season's identification", example = "0", position = 3)
	Long seasonId;
	@ApiModelProperty(notes = "Player's identification", example = "1", position = 4)
	Long playerId;
	@ApiModelProperty(notes = "Player's name", example = "Gica Craioveanu", position = 5)
	String playerName;
	@ApiModelProperty(notes = "Player's dorsal number in a Team-Season", example = "10", position = 6)
	Integer dorsalNumber;
	@ApiModelProperty(notes = "Player's is active in a Team-Season", example = "true", position = 1)
	Boolean active;
}
