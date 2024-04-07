package it.academy.services;

import it.academy.DTO.request.LoginUserDTO;
import it.academy.DTO.request.RegUserDTO;
import it.academy.DTO.request.UpdateUserDTO;
import it.academy.DTO.response.LoginUserJwtDTO;
import it.academy.DTO.response.UserDTO;
import it.academy.DTO.response.UsersDTO;
import lombok.NonNull;

public interface UserService {
    UserDTO getUserById(@NonNull Long id);
    void regUser(@NonNull RegUserDTO regUserDTO);
    LoginUserJwtDTO loginUser(@NonNull LoginUserDTO loginUserDTO);
    LoginUserJwtDTO reLoginUser(@NonNull String refreshToken);
    void appendRole(@NonNull String roleName, @NonNull Long userId);
    void deleteUser(@NonNull Long id);
    void updateUser(@NonNull UpdateUserDTO dto);
    UsersDTO getUsersPage(@NonNull Integer countPerPage, @NonNull Integer pageNum);
}
