package com.leaguemanagement.commons.leaguebase.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class RoundRequest implements Serializable {
	@ApiModelProperty(notes = "Round's description", example = "Jornada 25", position = 5)
	private String description;
	@ApiModelProperty(notes = "Round's date start", example = "2019-05-05", position = 6)
	private Date dateStart;
	@ApiModelProperty(notes = "Round's date end", example = "2019-05-11", position = 7)
	private Date dateEnd;
}
