package com.leaguemanagement.commons.leaguebase.dto.response;

import com.leaguemanagement.commons.leaguebase.dto.enums.MatchStatus;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import lombok.Data;

@Data
public class MatchSimpleResponse implements Serializable {
	@ApiModelProperty(notes = "Match's identification", example = "1", position = 0)
	Long id;
	@ApiModelProperty(notes = "Match's home team ID", example = "25", position = 1)
	Long homeTeamId;
	@ApiModelProperty(notes = "Match's home team name", example = "Real Madrid CF", position = 2)
	Long visitantTeamId;
	@ApiModelProperty(notes = "Match's date", example = "2019-05-18 22:00:00", position = 3)
	Instant dateMatch;
	@ApiModelProperty(notes = "Match's round ID", example = "15", position = 4)
	Long roundId;
	@ApiModelProperty(notes = "Match's field name", example = "Santiago Bernabeu", position = 5)
	String fieldName;
	@ApiModelProperty(notes = "Match's referee ID", example = "5", position = 6)
	Long refereeId;
	@ApiModelProperty(notes = "Match's status", example = "FINISHED", position = 7)
	MatchStatus status;
}
