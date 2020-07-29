package com.leaguemanagement.commons.leaguebase.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class RefereeResponse implements Serializable {
	@ApiModelProperty(notes = "Referee's identification", example = "0", position = 0)
	Long id;
	@ApiModelProperty(notes = "Referee's document", example = "00000000A", position = 1)
	String document;
	@ApiModelProperty(notes = "Referee's name", example = "Mateu Lahoz", position = 2)
	String name;
	@ApiModelProperty(notes = "Referee's date of birth", example = "1970-01-01", position = 3)
	Date dateBirth;
	@ApiModelProperty(notes = "Referee's nationality", example = "Spanish", position = 4)
	String nationality;
}
