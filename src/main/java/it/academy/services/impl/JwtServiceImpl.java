package it.academy.services.impl;

import io.jsonwebtoken.ExpiredJwtException;
import it.academy.Components.JwtProvider;
import it.academy.DAO.RefreshTokenDAO;
import it.academy.DAO.UserDAO;
import it.academy.DAO.impl.RefreshTokenDAOImpl;
import it.academy.DAO.impl.UserDAOImpl;
import it.academy.DTO.response.LoginUserJwtDTO;
import it.academy.exceptions.RefreshTokenExistException;
import it.academy.exceptions.TokenNotFound;
import it.academy.exceptions.UserNotFoundException;
import it.academy.models.RefreshToken;
import it.academy.models.User;
import it.academy.services.JwtService;
import it.academy.utilities.TransactionHelper;
import lombok.NonNull;

import java.util.function.Supplier;

public class JwtServiceImpl implements JwtService {

    private final RefreshTokenDAO refreshTokenDAO = new RefreshTokenDAOImpl();
    private final JwtProvider jwtProvider = new JwtProvider();
    private final UserDAO userDAO = new UserDAOImpl();
    private final TransactionHelper transactionHelper = TransactionHelper.getTransactionHelper();

    public void saveToken(@NonNull String refreshToken){
        Runnable supplier = () -> {
            String email = jwtProvider.getRefreshClaims(refreshToken).getSubject();
            if (refreshTokenDAO.existTokenByEmail(email)) {
                RefreshToken refreshToken1 = refreshTokenDAO.getTokenByEmail(email);
                if (refreshToken1.getRefreshToken().equals(refreshToken)) {
                    throw new RefreshTokenExistException();
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
        transactionHelper.transaction(supplier);
    }

    public LoginUserJwtDTO updateTokens(@NonNull String refreshToken){
        Supplier<LoginUserJwtDTO> supplier = () -> {
            jwtProvider.validateRefreshToken(refreshToken);
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
            refreshToken1.setRefreshToken(loginUserJwtDTO.getRefreshToken());
            refreshTokenDAO.update(refreshToken1);
            return loginUserJwtDTO;
        };
        return transactionHelper.transaction(supplier);
    }

    public LoginUserJwtDTO getPairOfTokens(@NonNull User user){
        return LoginUserJwtDTO.builder()
                .refreshToken(jwtProvider.generateRefreshToken(user))
                .accessToken(jwtProvider.generateAccessToken(user))
                .build();
    }


}
