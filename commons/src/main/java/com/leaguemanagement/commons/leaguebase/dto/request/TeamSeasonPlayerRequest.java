package com.leaguemanagement.commons.leaguebase.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class TeamSeasonPlayerRequest implements Serializable {
	@ApiModelProperty(notes = "Player's dorsal number in a Team-Season", example = "10", position = 0)
	Integer dorsalNumber;
	@ApiModelProperty(notes = "Player's is active in a Team-Season", example = "true", position = 1)
	Boolean active;
}
