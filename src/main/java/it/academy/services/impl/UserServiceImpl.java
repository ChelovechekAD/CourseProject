package it.academy.services.impl;

import it.academy.DAO.UserDAO;
import it.academy.DAO.impl.UserDAOImpl;
import it.academy.DTO.request.RequestDataDetailsDTO;
import it.academy.DTO.request.UpdateUserDTO;
import it.academy.DTO.response.UserDTO;
import it.academy.DTO.response.UsersDTO;
import it.academy.enums.RoleEnum;
import it.academy.exceptions.UserNotFoundException;
import it.academy.models.User;
import it.academy.services.UserService;
import it.academy.utilities.Converter;
import it.academy.utilities.TransactionHelper;
import lombok.NonNull;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final TransactionHelper transactionHelper;
    private final UserDAO userDAO;

    public UserServiceImpl() {
        transactionHelper = new TransactionHelper();
        userDAO = new UserDAOImpl(transactionHelper);
    }

    public void deleteUser(@NonNull Long id) {
        transactionHelper.transaction(() -> userDAO.delete(id));

    }

    public void updateUser(@NonNull UpdateUserDTO dto) {
        Runnable supplier = () -> {
            User user = userDAO.get(dto.getId());
            if (user == null) {
                throw new UserNotFoundException();
            }
            Converter.updateUserByDTO(user, dto);
        };
        transactionHelper.transaction(supplier);
    }

    public void appendRole(@NonNull String roleName, @NonNull Long userId) {
        RoleEnum roleEnum = RoleEnum.valueOf(roleName);
        //TODO Write this method.
    }

    public UserDTO getUserById(@NonNull Long id) {
        Supplier<UserDTO> supplier = () -> {
            User user = userDAO.get(id);
            if (user == null) {
                throw new UserNotFoundException();
            }
            return Converter.convertUserEntityToDTO(user);
        };
        return transactionHelper.transaction(supplier);
    }

    public UsersDTO getUsersPage(@NonNull RequestDataDetailsDTO requestDataDetailsDTO) {
        Supplier<UsersDTO> supplier = () -> {
            List<User> users = userDAO.getPage(requestDataDetailsDTO.getCountPerPage(),
                    requestDataDetailsDTO.getPageNum());
            Long count = userDAO.getCountOf();
            List<UserDTO> userDTOList = users.stream()
                    .map(Converter::convertUserEntityToDTO)
                    .collect(Collectors.toList());
            return new UsersDTO(userDTOList, count);
        };
        return transactionHelper.transaction(supplier);
    }

}
