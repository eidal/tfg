package com.leaguemanagement.commons.leaguebase.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class MatchRequest implements Serializable {
	@ApiModelProperty(notes = "Match's home team ID", example = "25", position = 0, required = true)
	@NotNull(message = "Match's home team ID is mandatory!")
	Long homeTeamId;
	@ApiModelProperty(notes = "Match's visitant team ID", example = "22", position = 1, required = true)
	@NotNull(message = "Match's visitant team ID is mandatory!")
	Long visitantTeamId;
	@ApiModelProperty(notes = "Match's date", example = "2019-05-18 22:00:00", position = 2)
	Instant dateMatch;
	@ApiModelProperty(notes = "Match's round ID", example = "14", position = 3, required = true)
	@NotNull(message = "Match's round ID is mandatory!")
	Long roundId;
	@ApiModelProperty(notes = "Match's field name", example = "Santiago Bernabeu", position = 4)
	@Size(max = 50, message = "Match's field name should not be greather than 50 characters")
	String fieldName;
	@ApiModelProperty(notes = "Match's referee ID", example = "5", position = 5)
	Long refereeId;
}
