package it.academy.services.impl;

import it.academy.Components.JwtProvider;
import it.academy.DAO.RefreshTokenDAO;
import it.academy.DAO.RoleDAO;
import it.academy.DAO.UserDAO;
import it.academy.DAO.impl.RefreshTokenDAOImpl;
import it.academy.DAO.impl.RoleDAOImpl;
import it.academy.DAO.impl.UserDAOImpl;
import it.academy.DTO.request.LoginUserDTO;
import it.academy.DTO.request.RegUserDTO;
import it.academy.DTO.request.UpdateUserDTO;
import it.academy.DTO.response.LoginUserJwtDTO;
import it.academy.DTO.response.UserDTO;
import it.academy.DTO.response.UsersDTO;
import it.academy.enums.RoleEnum;
import it.academy.exceptions.*;
import it.academy.models.RefreshToken;
import it.academy.models.Role;
import it.academy.models.User;
import it.academy.services.AuthService;
import it.academy.utilities.Converter;
import it.academy.utilities.TransactionHelper;
import lombok.NonNull;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AuthServiceImpl implements AuthService {

    private final TransactionHelper transactionHelper = TransactionHelper.getTransactionHelper();
    private final UserDAO userDAO = new UserDAOImpl();
    private final RoleDAO roleDAO = new RoleDAOImpl();
    private final RefreshTokenDAO refreshTokenDAO = new RefreshTokenDAOImpl();
    private final JwtProvider jwtProvider = new JwtProvider();

    public void regUser(@NonNull RegUserDTO regUserDTO){
        System.out.println(regUserDTO);
        Runnable supplier = () -> {
            if (!Objects.equals(regUserDTO.getPassword(), regUserDTO.getPasswordConfirm())){
                throw new PasswordMatchException();
            }
            if (userDAO.existUserByEmail(regUserDTO.getEmail())){
                throw new UserExistException();
            }
            User user = Converter.convertRegUserDTOToEntity(regUserDTO);
            Role role = roleDAO.getByRoleName(RoleEnum.DEFAULT_USER);
            user.getRoleSet().add(role);
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            userDAO.create(user);
        };

        transactionHelper.transaction(supplier);
    }

    public LoginUserJwtDTO loginUser(@NonNull LoginUserDTO loginUserDTO){
        Supplier<LoginUserJwtDTO> supplier = ()-> {
            if (!userDAO.existUserByEmail(loginUserDTO.getEmail())) {
                throw new UserNotFoundException();
            }
            User user = userDAO.getUserByEmail(loginUserDTO.getEmail());
            if (!BCrypt.checkpw(loginUserDTO.getPassword(), user.getPassword())) {
                throw new WrongPasswordException();
            }
            LoginUserJwtDTO loginUserJwtDTO = getPairOfTokens(user);
            loginUserJwtDTO.setUserDTO(Converter.convertUserEntityToDTO(user));
            saveToken(loginUserJwtDTO.getRefreshToken()).run();
            return loginUserJwtDTO;
        };
        return transactionHelper.transaction(supplier);
    }

    public LoginUserJwtDTO reLoginUser(@NonNull String refreshToken){
        return transactionHelper.transaction(updateTokens(refreshToken));
    }

    public void deleteUser(@NonNull Long id){
        transactionHelper.transaction(()->userDAO.delete(id));

    }

    public void updateUser(@NonNull UpdateUserDTO dto){
        Runnable supplier = () -> {
            User user = userDAO.get(dto.getId());
            if (user == null) {
                throw new UserNotFoundException();
            }
            Converter.updateUserByDTO(user, dto);
        };
        transactionHelper.transaction(supplier);
    }

    public void appendRole(@NonNull String roleName, @NonNull Long userId){
        RoleEnum roleEnum = RoleEnum.valueOf(roleName);
        //TODO Write this method.
    }

    public UserDTO getUserById(@NonNull Long id){
        Supplier<UserDTO> supplier = () -> {
            User user = userDAO.get(id);
            if (user == null) {
                throw new UserNotFoundException();
            }
            return Converter.convertUserEntityToDTO(user);
        };
        return transactionHelper.transaction(supplier);
    }

    public UsersDTO getUsersPage(@NonNull Integer countPerPage, @NonNull Integer pageNum){
        Supplier<UsersDTO> supplier = () -> {
            List<User> users = userDAO.getPage(countPerPage, pageNum);
            Long count = userDAO.getCountOf();
            List<UserDTO> userDTOList = users.stream()
                    .map(Converter::convertUserEntityToDTO)
                    .collect(Collectors.toList());
            return new UsersDTO(userDTOList, count);
        };
        return transactionHelper.transaction(supplier);
    }

    private Runnable saveToken(@NonNull String refreshToken){
        return () -> {
            String email = jwtProvider.getRefreshClaims(refreshToken).getSubject();
            if (refreshTokenDAO.existTokenByEmail(email)) {
                RefreshToken refreshToken1 = refreshTokenDAO.getTokenByEmail(email);
                if (!refreshToken1.getRefreshToken().equals(refreshToken)) {
                    return;
                }
                refreshToken1.setRefreshToken(refreshToken);
                refreshTokenDAO.update(refreshToken1);
            } else {
                RefreshToken refreshToken1 = RefreshToken.builder()
                        .refreshToken(refreshToken)
                        .userEmail(email)
                        .build();
                refreshTokenDAO.create(refreshToken1);
            }
        };
    }

    private Supplier<LoginUserJwtDTO> updateTokens(@NonNull String refreshToken){
        return () -> {
            if(!jwtProvider.validateRefreshToken(refreshToken)) {
                throw new RefreshTokenInvalidException();
            }
            String email = jwtProvider.getRefreshClaims(refreshToken).getSubject();
            if (!userDAO.existUserByEmail(email)) {
                throw new UserNotFoundException();
            }
            if (!refreshTokenDAO.existTokenByEmail(email)) {
                throw new TokenNotFound();
            }
            User user = userDAO.getUserByEmail(email);
            RefreshToken refreshToken1 = refreshTokenDAO.getTokenByEmail(email);
            LoginUserJwtDTO loginUserJwtDTO = getPairOfTokens(user);
            loginUserJwtDTO.setUserDTO(Converter.convertUserEntityToDTO(user));
            refreshToken1.setRefreshToken(loginUserJwtDTO.getRefreshToken());
            refreshTokenDAO.update(refreshToken1);
            return loginUserJwtDTO;
        };
    }

    private LoginUserJwtDTO getPairOfTokens(@NonNull User user){
        return LoginUserJwtDTO.builder()
                .refreshToken(jwtProvider.generateRefreshToken(user))
                .accessToken(jwtProvider.generateAccessToken(user))
                .build();
    }
}
