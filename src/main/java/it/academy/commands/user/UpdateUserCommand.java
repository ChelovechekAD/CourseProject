package it.academy.commands.user;

import it.academy.DTO.request.UpdateUserDTO;
import it.academy.commands.Command;
import it.academy.services.UserService;
import it.academy.services.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

import static it.academy.utilities.Constants.GSON;

public class UpdateUserCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String req = request.getReader().lines().collect(Collectors.joining());
        System.out.println(req);
        UpdateUserDTO updateUserDTO = GSON.fromJson(req, UpdateUserDTO.class);
        UserService userService = new UserServiceImpl();
        userService.updateUser(updateUserDTO);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
