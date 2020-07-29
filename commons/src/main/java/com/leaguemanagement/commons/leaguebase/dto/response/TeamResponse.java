package com.leaguemanagement.commons.leaguebase.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class TeamResponse implements Serializable {
	@ApiModelProperty(notes = "Team's identification", example = "0", position = 0)
	Long id;
	@ApiModelProperty(notes = "Team's name", example = "Liga BBVA", position = 1)
	String name;
	@ApiModelProperty(notes = "Team's shield", example = "http://urlimage/shield.jpg", position = 2)
	String shield;
}
