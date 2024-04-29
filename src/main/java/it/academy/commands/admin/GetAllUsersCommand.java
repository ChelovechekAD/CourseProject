package it.academy.commands.admin;

import it.academy.DTO.request.RequestDataDetailsDTO;
import it.academy.DTO.response.UsersDTO;
import it.academy.commands.Command;
import it.academy.services.UserService;
import it.academy.services.impl.UserServiceImpl;
import it.academy.utilities.Extractor;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static it.academy.utilities.Constants.GSON;

public class GetAllUsersCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestDataDetailsDTO dtoIn = Extractor.extractDTOFromRequest(request, new RequestDataDetailsDTO());
        UserService userService = new UserServiceImpl();
        UsersDTO dtoOut = userService.getUsersPage(dtoIn);
        ResponseHelper.sendJsonResponse(response, GSON.toJson(dtoOut));
    }
}
