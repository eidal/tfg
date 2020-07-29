package com.leaguemanagement.commons.leaguebase.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class TeamSeasonSimpleResponse implements Serializable {
	@ApiModelProperty(notes = "Team's identification", example = "0", position = 0)
	private Long teamId;
	@ApiModelProperty(notes = "Team's name", example = "Atlético de Madrid", position = 1)
	private String teamName;
}
