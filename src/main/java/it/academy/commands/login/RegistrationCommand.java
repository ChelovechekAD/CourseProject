package it.academy.commands.login;

import it.academy.DTO.request.RegUserDTO;
import it.academy.commands.Command;
import it.academy.exceptions.PasswordMatchException;
import it.academy.exceptions.UserExistException;
import it.academy.services.AuthService;
import it.academy.services.impl.AuthServiceImpl;
import it.academy.utilities.Constants;
import it.academy.utilities.ConverterJson;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

public class RegistrationCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            AuthService authService = new AuthServiceImpl();
            String req = request.getReader().lines().collect(Collectors.joining());
            System.out.println(req);
            RegUserDTO userDTO = ConverterJson.convertJsonRegReqToDTO(req);
            authService.regUser(userDTO);
            ResponseHelper.sendResponseWithStatus(response, HttpServletResponse.SC_CREATED, Constants.SUCCESSFULLY_CREATED);
        } catch (PasswordMatchException | UserExistException e){
            e.printStackTrace();
            ResponseHelper.sendResponseWithStatus(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
