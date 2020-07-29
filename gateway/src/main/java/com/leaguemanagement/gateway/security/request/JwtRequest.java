package com.leaguemanagement.gateway.security.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtRequest implements Serializable {

  @NotNull
  private String user;
  @NotNull
  private String password;

}
