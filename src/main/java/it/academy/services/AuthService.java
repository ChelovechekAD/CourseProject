package it.academy.services;

import it.academy.DTO.request.LoginUserDTO;
import it.academy.DTO.request.RegUserDTO;
import it.academy.DTO.request.RequestDataDetailsDTO;
import it.academy.DTO.request.UpdateUserDTO;
import it.academy.DTO.response.LoginUserJwtDTO;
import it.academy.DTO.response.UserDTO;
import it.academy.DTO.response.UsersDTO;
import lombok.NonNull;

public interface AuthService {
    void regUser(@NonNull RegUserDTO regUserDTO);
    LoginUserJwtDTO loginUser(@NonNull LoginUserDTO loginUserDTO);
    LoginUserJwtDTO reLoginUser(@NonNull String refreshToken);

}
