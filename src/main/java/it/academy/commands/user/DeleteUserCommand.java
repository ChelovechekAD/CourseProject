package it.academy.commands.user;

import it.academy.commands.Command;
import it.academy.exceptions.UserNotFoundException;
import it.academy.models.User;
import it.academy.services.UserService;
import it.academy.services.impl.UserServiceImpl;
import it.academy.utilities.Constants;
import it.academy.utilities.Extractor;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;

import java.io.IOException;

public class DeleteUserCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = Extractor.extractSingleParamFromRequest(request, Constants.USER_ID_PARAM_KEY, Long.class);
        UserService userService = new UserServiceImpl();
        try {
            userService.deleteUser(userId);
        }catch (UserNotFoundException e){
            ResponseHelper.sendResponseWithStatus(response, HttpStatus.SC_NOT_FOUND, e.getMessage());
        }
    }
}
