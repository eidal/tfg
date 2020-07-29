package com.leaguemanagement.commons.leaguebase.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class RoundResponse implements Serializable {
	@ApiModelProperty(notes = "Round's identification", example = "1", position = 0)
	Long id;
	@ApiModelProperty(notes = "Round's competition ID", example = "2", position = 1)
	private Long competitionId;
	@ApiModelProperty(notes = "Round's competition name", example = "Liga BBVA", position = 2)
	private String competitionName;
	@ApiModelProperty(notes = "Round's season ID", example = "3", position = 3)
	private Long seasonId;
	@ApiModelProperty(notes = "Round's number", example = "25", position = 4)
	private Integer number;
	@ApiModelProperty(notes = "Round's description", example = "Jornada 25", position = 5)
	private String description;
	@ApiModelProperty(notes = "Round's date start", example = "2019-05-05", position = 6)
	private Date dateStart;
	@ApiModelProperty(notes = "Round's date end", example = "2019-05-11", position = 7)
	private Date dateEnd;
}
