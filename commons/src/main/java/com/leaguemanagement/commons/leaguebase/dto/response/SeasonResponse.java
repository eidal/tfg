package com.leaguemanagement.commons.leaguebase.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class SeasonResponse implements Serializable {
	@ApiModelProperty(notes = "Season's identification", example = "0", position = 0)
	Long id;
	@ApiModelProperty(notes = "Season's year start", example = "2018", position = 1)
	Integer yearStart;
	@ApiModelProperty(notes = "Season's year end", example = "2019", position = 2)
	Integer yearEnd;
}
