package it.academy.commands.user;

import it.academy.commands.Command;
import it.academy.services.UserService;
import it.academy.services.impl.UserServiceImpl;
import it.academy.utilities.Constants;
import it.academy.utilities.Extractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteUserCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = Extractor.extractSingleParamFromRequest(request, Constants.USER_ID_PARAM_KEY, Long.class);
        UserService userService = new UserServiceImpl();
        userService.deleteUser(userId);
    }
}
