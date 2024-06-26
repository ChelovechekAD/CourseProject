package it.academy.commands.login;

import it.academy.DTO.request.RegUserDTO;
import it.academy.commands.Command;
import it.academy.services.AuthService;
import it.academy.services.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

import static it.academy.utilities.Constants.GSON;

public class RegistrationCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthService authService = new AuthServiceImpl();
        String req = request.getReader().lines().collect(Collectors.joining());
        RegUserDTO userDTO = GSON.fromJson(req, RegUserDTO.class);
        authService.regUser(userDTO);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
