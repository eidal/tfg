package com.leaguemanagement.commons.leaguebase.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class PlayerSimpleResponse implements Serializable {
	@ApiModelProperty(notes = "Player's identification", example = "0", position = 0)
	Long id;
	@ApiModelProperty(notes = "Player's name", example = "Pelé", position = 1)
	String name;
	@ApiModelProperty(notes = "Player's age", example = "23", position = 2)
	Integer age;
}
