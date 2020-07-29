package com.leaguemanagement.commons.leaguebase.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class CompetitionSeasonResponse implements Serializable {
	@ApiModelProperty(notes = "Competition's ID", example = "25", position = 0)
	Long competitionId;
	@ApiModelProperty(notes = "Competition's name", example = "Liga BBVA", position = 1)
	String competitionName;
	@ApiModelProperty(notes = "Season's ID", example = "15", position = 2)
	Long seasonId;
	@ApiModelProperty(notes = "Competition's number of teams", example = "20", position = 3)
	Integer teamsNumber;
	@ApiModelProperty(notes = "Competition's number of returns", example = "2", position = 4)
	Integer returnsNumber;
	@ApiModelProperty(notes = "Competition's points gain a team with a victory", example = "3", position = 5)
	Integer pointsWin;
	@ApiModelProperty(notes = "Competition's points gain a team with a tie", example = "1", position = 6)
	Integer pointsTie;
}
