package com.leaguemanagement.commons.leaguebase.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SeasonRequest implements Serializable {
	@ApiModelProperty(notes = "Season's year start", example = "2018", position = 0, required = true)
	@NotNull(message = "Season's year start is mandatory")
	@Digits(integer = 4, fraction = 0, message = "Season's year start should be numeric")
	Integer yearStart;
	@ApiModelProperty(notes = "Season's year end", example = "2019", position = 1, required = true)
	@NotNull(message = "Season's year end is mandatory")
	@Digits(integer = 4, fraction = 0,  message = "Season's year end should be numeric")
	Integer yearEnd;
}
