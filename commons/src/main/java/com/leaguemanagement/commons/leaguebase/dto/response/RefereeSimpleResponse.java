package com.leaguemanagement.commons.leaguebase.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class RefereeSimpleResponse implements Serializable {
	@ApiModelProperty(notes = "Referee's identification", example = "0", position = 0)
	Long id;
	@ApiModelProperty(notes = "Referee's name", example = "Mateu Lahoz", position = 2)
	String name;
}
