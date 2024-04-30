package it.academy.commands.user;

import it.academy.DTO.response.UserDTO;
import it.academy.commands.Command;
import it.academy.services.UserService;
import it.academy.services.impl.UserServiceImpl;
import it.academy.utilities.Constants;
import it.academy.utilities.Extractor;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static it.academy.utilities.Constants.GSON;

public class GetUserCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = Extractor.extractSingleParamFromRequest(request, Constants.USER_ID_PARAM_KEY, Long.class);
        UserService userService = new UserServiceImpl();
        UserDTO dtoOut = userService.getUserById(userId);
        ResponseHelper.sendJsonResponse(response, GSON.toJson(dtoOut));
    }
}
