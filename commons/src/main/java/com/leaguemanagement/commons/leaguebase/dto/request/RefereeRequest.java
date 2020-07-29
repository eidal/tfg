package com.leaguemanagement.commons.leaguebase.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class RefereeRequest implements Serializable {
	@ApiModelProperty(notes = "Referee's document", example = "00000000A", position = 0, required = true)
	@NotEmpty(message = "Referee's document is mandatory")
	@Size(max = 25, message = "Referee's document should not be greather than 25 characters")
	String document;
	@ApiModelProperty(notes = "Referee's name", example = "Mateu Lahoz", position = 1, required = true)
	@NotEmpty(message = "Referee's name is mandatory")
	@Size(max = 50, message = "Referee's name should not be greather than 50 characters")
	String name;
	@ApiModelProperty(notes = "Referee's date of birth", example = "1970-01-01", position = 2)
	Date dateBirth;
	@ApiModelProperty(notes = "Referee's nationality", example = "Spanish", position = 3, required = true)
	@NotEmpty(message = "Referee's nationality is mandatory")
	@Size(max = 30, message = "Referee's nationality should not be greather than 30 characters")
	String nationality;
}
