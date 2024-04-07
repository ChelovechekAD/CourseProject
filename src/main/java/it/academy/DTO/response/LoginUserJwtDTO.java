package it.academy.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUserJwtDTO {

    private String type = "Bearer";
    private String accessToken;
    private String refreshToken;

}
