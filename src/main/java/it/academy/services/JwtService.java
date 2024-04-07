package it.academy.services;

import it.academy.DTO.response.LoginUserJwtDTO;
import it.academy.models.User;
import lombok.NonNull;

public interface JwtService {

    void saveToken(@NonNull String refreshToken);
    LoginUserJwtDTO updateTokens(@NonNull String refreshToken);
    LoginUserJwtDTO getPairOfTokens(@NonNull User user);


}
