package it.academy.services.impl;

import it.academy.DAO.RefreshTokenDAO;
import it.academy.DAO.RoleDAO;
import it.academy.DAO.UserDAO;
import it.academy.DAO.impl.RefreshTokenDAOImpl;
import it.academy.DAO.impl.RoleDAOImpl;
import it.academy.DAO.impl.UserDAOImpl;
import it.academy.DTO.request.LoginUserDTO;
import it.academy.DTO.request.RegUserDTO;
import it.academy.DTO.response.LoginUserJwtDTO;
import it.academy.enums.RoleEnum;
import it.academy.exceptions.*;
import it.academy.models.RefreshToken;
import it.academy.models.Role;
import it.academy.models.User;
import it.academy.services.AuthService;
import it.academy.utilities.Converter;
import it.academy.utilities.TransactionHelper;
import it.academy.сomponents.JwtProvider;
import lombok.NonNull;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;
import java.util.function.Supplier;

public class AuthServiceImpl implements AuthService {

    private final TransactionHelper transactionHelper;
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final RefreshTokenDAO refreshTokenDAO;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl() {
        transactionHelper = new TransactionHelper();
        userDAO = new UserDAOImpl(transactionHelper);
        roleDAO = new RoleDAOImpl(transactionHelper);
        refreshTokenDAO = new RefreshTokenDAOImpl(transactionHelper);
        jwtProvider = new JwtProvider();
    }

    public void regUser(@NonNull RegUserDTO regUserDTO) {
        System.out.println(regUserDTO);
        Runnable supplier = () -> {
            if (!Objects.equals(regUserDTO.getPassword(), regUserDTO.getPasswordConfirm())) {
                throw new PasswordMatchException();
            }
            if (userDAO.existUserByEmail(regUserDTO.getEmail())) {
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

    public LoginUserJwtDTO loginUser(@NonNull LoginUserDTO loginUserDTO) {
        Supplier<LoginUserJwtDTO> supplier = () -> {
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

    public LoginUserJwtDTO reLoginUser(@NonNull String refreshToken) {
        return transactionHelper.transaction(updateTokens(refreshToken));
    }


    private Runnable saveToken(@NonNull String refreshToken) {
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

    private Supplier<LoginUserJwtDTO> updateTokens(@NonNull String refreshToken) {
        return () -> {
            if (!jwtProvider.validateRefreshToken(refreshToken)) {
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

    private LoginUserJwtDTO getPairOfTokens(@NonNull User user) {
        return LoginUserJwtDTO.builder()
                .refreshToken(jwtProvider.generateRefreshToken(user))
                .accessToken(jwtProvider.generateAccessToken(user))
                .build();
    }
}
