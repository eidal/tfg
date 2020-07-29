package com.leaguemanagement.commons.leaguebase.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class CompetitionResponse implements Serializable {
	@ApiModelProperty(notes = "Competition's identification", example = "1", position = 0)
	Long id;
	@ApiModelProperty(notes = "Competition's name", example = "Liga BBVA", position = 1)
	String name;
}
