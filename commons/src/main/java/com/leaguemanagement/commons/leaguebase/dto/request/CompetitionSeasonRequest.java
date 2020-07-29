package com.leaguemanagement.commons.leaguebase.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompetitionSeasonRequest implements Serializable {
	@ApiModelProperty(notes = "Competition's number of teams", example = "20", position = 2, required = true)
	@NotNull(message = "CompetitionSeason's teamsNumber is mandatory!")
	Integer teamsNumber;
	@ApiModelProperty(notes = "Competition's number of returns", example = "2", position = 3, required = true)
	@NotNull(message = "CompetitionSeason's returnsNumber is mandatory!")
	Integer returnsNumber;
	@ApiModelProperty(notes = "Competition's points gain a team with a victory", example = "3", position = 4, required = true)
	@NotNull(message = "CompetitionSeason's pointsWin is mandatory!")
	Integer pointsWin;
	@ApiModelProperty(notes = "Competition's points gain a team with a tie", example = "1", position = 5, required = true)
	@NotNull(message = "CompetitionSeason's pointsTie is mandatory!")
	Integer pointsTie;
}
