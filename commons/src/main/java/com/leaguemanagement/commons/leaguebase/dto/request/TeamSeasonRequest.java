package com.leaguemanagement.commons.leaguebase.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TeamSeasonRequest implements Serializable {
	@ApiModelProperty(notes = "Team's president name", example = "Enrique Cerezo", position = 0)
	String presidentName;
	@ApiModelProperty(notes = "Team's coach name", example = "Cholo Simeone", position = 1, required = true)
	@NotEmpty(message = "Team's coach name is mandatory!")
	String coachName;
	@ApiModelProperty(notes = "Team's first equipment description", example = "Short blue - Tshirt red and white", position = 2)
	String firstEquipmentDescription;
	@ApiModelProperty(notes = "Team's second equipment description", example = "Short black - Tshirt black", position = 3)
	String secondEquipmentDescription;
}
