package com.leaguemanagement.commons.leaguebase.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class TeamRequest implements Serializable {
	@ApiModelProperty(notes = "Team's name", example = "Liga BBVA", position = 0, required = true)
	@NotEmpty(message = "Team's name is mandatory")
	@Size(max = 50, message = "Team's name should not be greater than 50 characters")
	String name;
	@ApiModelProperty(notes = "Team's shield", example = "http://urlimage/shield.jpg", position = 1)
	@Size(max = 200, message = "Team's shield should not be greater than 200 characters")
	String shield;
}
