package it.academy.services;

import it.academy.DTO.request.LoginUserDTO;
import it.academy.DTO.request.RegUserDTO;
import it.academy.DTO.response.LoginUserJwtDTO;
import lombok.NonNull;

public interface AuthService {
    void regUser(@NonNull RegUserDTO regUserDTO);

    LoginUserJwtDTO loginUser(@NonNull LoginUserDTO loginUserDTO);

    LoginUserJwtDTO reLoginUser(@NonNull String refreshToken);

}
