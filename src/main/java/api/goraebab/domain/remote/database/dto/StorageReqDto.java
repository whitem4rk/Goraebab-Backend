package api.goraebab.domain.remote.database.dto;

import api.goraebab.domain.remote.database.entity.DBMS;
import api.goraebab.global.util.validator.ValidEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StorageReqDto {

  private static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{1,32}$";
  private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z\\d!@#$%^&*()_+]{8,128}$";

  @NotBlank
  @Size(max = 255, message = "Host must be less than 255 characters")
  private String host;

  @NotNull
  @Positive
  @Max(value = 65535, message = "Port must be 1 ~ 65535")
  private Integer port;

  @ValidEnum(target = DBMS.class)
  private DBMS dbms;

  @NotBlank
  @Size(max = 255, message = "Name must be less than 255 characters")
  private String name;

  @NotBlank
  @Pattern(regexp = USERNAME_REGEX,
      message = "Username must be 1-32 characters long and can only contain letters, numbers, and underscores")
  private String username;

  @NotBlank
  @Pattern(regexp = PASSWORD_REGEX,
      message = "Password must be 8-128 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character.")
  private String password;


  @Builder
  public StorageReqDto(String host, Integer port, DBMS dbms, String name, String username,
      String password) {
    this.host = host;
    this.port = port;
    this.dbms = dbms;
    this.name = name;
    this.username = username;
    this.password = password;
  }

}
