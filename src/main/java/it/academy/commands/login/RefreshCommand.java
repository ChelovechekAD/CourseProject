package it.academy.commands.login;

import it.academy.DTO.response.LoginUserJwtDTO;
import it.academy.commands.Command;
import it.academy.exceptions.RefreshTokenInvalidException;
import it.academy.exceptions.TokenNotFound;
import it.academy.exceptions.UserNotFoundException;
import it.academy.services.AuthService;
import it.academy.services.impl.AuthServiceImpl;
import it.academy.utilities.Constants;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static it.academy.utilities.Constants.GSON;

public class RefreshCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            AuthService authService = new AuthServiceImpl();
            Optional<Cookie> cookie = Arrays.stream(request.getCookies()).filter(
                    c -> Objects.equals(c.getName(), Constants.REFRESH_TOKEN_ATTR_NAME))
                    .findFirst();
            String cookieValue = cookie.map(Cookie::getValue).orElse(null);
            if (cookieValue == null){
                ResponseHelper.sendResponseWithStatus(response, HttpServletResponse.SC_UNAUTHORIZED, Constants.UNAUTHORIZED);
                return;
            }
            LoginUserJwtDTO loginUserJwtDTO = authService.reLoginUser(cookieValue);
            Cookie updateCookie = new Cookie(Constants.REFRESH_TOKEN_ATTR_NAME, loginUserJwtDTO.getRefreshToken());
            updateCookie.setMaxAge(Constants.JWT_REFRESH_EXPIRATION*60);
            response.addCookie(updateCookie);
            String resp = GSON.toJson(loginUserJwtDTO);
            ResponseHelper.sendJsonResponse(response, resp);
        }catch (UserNotFoundException | TokenNotFound | RefreshTokenInvalidException e){
            Cookie deleteCookie = new Cookie(Constants.REFRESH_TOKEN_ATTR_NAME, "");
            deleteCookie.setMaxAge(0);
            response.addCookie(deleteCookie);
            ResponseHelper.sendResponseWithStatus(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
