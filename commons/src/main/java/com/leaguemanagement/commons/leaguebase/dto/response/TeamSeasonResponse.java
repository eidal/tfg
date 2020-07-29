package com.leaguemanagement.commons.leaguebase.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class TeamSeasonResponse implements Serializable {
	@ApiModelProperty(notes = "Team's identification", example = "0", position = 0)
	private Long teamId;
	@ApiModelProperty(notes = "Team's name", example = "Atlético de Madrid", position = 1)
	private String teamName;
	@ApiModelProperty(notes = "Season's identification", example = "0", position = 2)
	private Long seasonId;
	@ApiModelProperty(notes = "Team's president name", example = "Enrique Cerezo", position = 3)
	private String presidentName;
	@ApiModelProperty(notes = "Team's coach name", example = "Cholo Simeone", position = 4)
	private String coachName;
	@ApiModelProperty(notes = "Team's first equipment description", example = "Short blue - Tshirt red and white", position = 5)
	private String firstEquipmentDescription;
	@ApiModelProperty(notes = "Team's second equipment description", example = "Short black - Tshirt black", position = 6)
	private String secondEquipmentDescription;
}
