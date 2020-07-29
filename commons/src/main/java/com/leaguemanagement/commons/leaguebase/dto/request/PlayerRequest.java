package com.leaguemanagement.commons.leaguebase.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PlayerRequest implements Serializable {
	@ApiModelProperty(notes = "Player's document", example = "00000000A", position = 0, required = true)
	@NotEmpty(message = "Player's document is mandatory")
	@Size(max = 25, message = "Player's document should not be greather than 25 characters")
	String document;
	@ApiModelProperty(notes = "Player's name", example = "Pelé", position = 1, required = true)
	@NotEmpty(message = "Player's name is mandatory")
	@Size(max = 50, message = "Player's name should not be greather than 50 characters")
	String name;
	@ApiModelProperty(notes = "Player's date of birth", example = "1970-01-01", position = 2)
	Date dateBirth;
	@ApiModelProperty(notes = "Player's nationality", example = "Spanish", position = 3, required = true)
	@NotEmpty(message = "Player's nationality is mandatory")
	@Size(max = 30, message = "Player's nationality should not be greather than 30 characters")
	String nationality;
	@ApiModelProperty(notes = "Player's tshirt size", example = "XL", position = 4)
	@Size(max = 10, message = "Player's tshirt size should not be greather than 10 characters")
	String tshirtSize;
	@ApiModelProperty(notes = "Player's short size", example = "XL", position = 5)
	@Size(max = 10, message = "Player's short size should not be greather than 10 characters")
	String shortSize;
	@Size(max = 10, message = "Player's boot size should not be greather than 10 characters")
	@ApiModelProperty(notes = "Player's boot size", example = "45", position = 6)
	String bootSize;
}
