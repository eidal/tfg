package com.leaguemanagement.commons.leaguebase.dto.response;


import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class PlayerResponse implements Serializable {
	@ApiModelProperty(notes = "Player's identification", example = "0", position = 0)
	Long id;
	@ApiModelProperty(notes = "Player's document", example = "00000000A", position = 1)
	String document;
	@ApiModelProperty(notes = "Player's name", example = "Pelé", position = 2)
	String name;
	@ApiModelProperty(notes = "Player's date of birth", example = "1970-01-01", position = 3)
	Date dateBirth;
	@ApiModelProperty(notes = "Player's nationality", example = "Spanish", position = 4)
	String nationality;
	@ApiModelProperty(notes = "Player's tshirt size", example = "XL", position = 5)
	String tshirtSize;
	@ApiModelProperty(notes = "Player's short size", example = "XL", position = 6)
	String shortSize;
	@ApiModelProperty(notes = "Player's boot size", example = "45", position = 7)
	String bootSize;
}
