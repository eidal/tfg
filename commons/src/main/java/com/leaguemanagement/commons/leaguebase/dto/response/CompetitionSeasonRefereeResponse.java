package com.leaguemanagement.commons.leaguebase.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class CompetitionSeasonRefereeResponse implements Serializable {
	@ApiModelProperty(notes = "Competition's ID", example = "25", position = 0)
	Long competitionId;
	@ApiModelProperty(notes = "Competition's name", example = "Liga BBVA", position = 1)
	String competitionName;
	@ApiModelProperty(notes = "Season's ID", example = "15", position = 2)
	Long seasonId;
	@ApiModelProperty(notes = "Referee's ID", example = "20", position = 3)
	Long refereeId;
	@ApiModelProperty(notes = "Referee's name", example = "Mateu Lahoz", position = 3)
	String refereeName;
}
