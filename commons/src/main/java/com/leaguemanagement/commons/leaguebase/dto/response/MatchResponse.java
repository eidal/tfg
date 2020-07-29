package com.leaguemanagement.commons.leaguebase.dto.response;

import com.leaguemanagement.commons.leaguebase.dto.enums.MatchStatus;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import lombok.Data;

@Data
public class MatchResponse implements Serializable {
	@ApiModelProperty(notes = "Match's identification", example = "1", position = 0)
	Long id;
	@ApiModelProperty(notes = "Match's home team ID", example = "25", position = 1)
	Long homeTeamId;
	@ApiModelProperty(notes = "Match's home team name", example = "Real Madrid CF", position = 2)
	String homeTeamName;
	@ApiModelProperty(notes = "Match's visitant team ID", example = "22", position = 3)
	Long visitantTeamId;
	@ApiModelProperty(notes = "Match's visitant team name", example = "FC Barcelona", position = 4)
	String visitantTeamName;
	@ApiModelProperty(notes = "Match's date", example = "2019-05-18 22:00:00", position = 5)
	Instant dateMatch;
	@ApiModelProperty(notes = "Match's round ID", example = "15", position = 6)
	Long roundId;
	@ApiModelProperty(notes = "Match's round number", example = "20", position = 7)
	Integer roundNumber;
	@ApiModelProperty(notes = "Match's field name", example = "Santiago Bernabeu", position = 8)
	String fieldName;
	@ApiModelProperty(notes = "Match's referee ID", example = "5", position = 9)
	Long refereeId;
	@ApiModelProperty(notes = "Match's referee ID", example = "Mateu Lahoz", position = 10)
	Long refereeName;
	@ApiModelProperty(notes = "Match's status", example = "FINISHED", position = 11)
	MatchStatus status;
}
