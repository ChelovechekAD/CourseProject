package it.academy.services;

import it.academy.DTO.request.RequestDataDetailsDTO;
import it.academy.DTO.request.UpdateUserDTO;
import it.academy.DTO.response.UserDTO;
import it.academy.DTO.response.UsersDTO;
import lombok.NonNull;

public interface UserService {
    void appendRole(@NonNull String roleName, @NonNull Long userId);

    void deleteUser(@NonNull Long id);

    void updateUser(@NonNull UpdateUserDTO dto);

    UsersDTO getUsersPage(@NonNull RequestDataDetailsDTO requestDataDetailsDTO);

    UserDTO getUserById(@NonNull Long id);

}
