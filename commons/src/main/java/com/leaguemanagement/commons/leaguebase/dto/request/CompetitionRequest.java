package com.leaguemanagement.commons.leaguebase.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class CompetitionRequest implements Serializable {
	@ApiModelProperty(notes = "Competition's name", example = "Liga BBVA", position = 0, required = true)
	@NotEmpty(message = "Competition's name is mandatory")
	@Size(max = 50, message = "Competition's name should not be greater than 50 characters")
	String name;
}
