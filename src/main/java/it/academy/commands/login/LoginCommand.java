package it.academy.commands.login;

import it.academy.DTO.request.LoginUserDTO;
import it.academy.DTO.response.LoginUserJwtDTO;
import it.academy.commands.Command;
import it.academy.services.AuthService;
import it.academy.services.impl.AuthServiceImpl;
import it.academy.utilities.Constants;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

import static it.academy.utilities.Constants.GSON;

public class LoginCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthService authService = new AuthServiceImpl();
        String req = request.getReader().lines().collect(Collectors.joining());
        LoginUserDTO loginUserDTO = GSON.fromJson(req, LoginUserDTO.class);
        LoginUserJwtDTO dto = authService.loginUser(loginUserDTO);
        String resp = GSON.toJson(dto);
        Cookie token = new Cookie(Constants.REFRESH_TOKEN_ATTR_NAME, dto.getRefreshToken());
        token.setMaxAge(Constants.JWT_REFRESH_EXPIRATION * Constants.MULTI_TIME);
        response.addCookie(token);
        ResponseHelper.sendJsonResponse(response, resp);
    }
}
