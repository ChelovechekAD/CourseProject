package it.academy.servlets;

import it.academy.commands.Command;
import it.academy.commands.factory.CommandFactory;
import it.academy.services.AdminService;
import it.academy.services.impl.AdminServiceImpl;
import it.academy.utilities.Constants;
import it.academy.utilities.DevUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "MainServlet", urlPatterns = "/api/*")
public class MainServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        proceedRequest(req, resp, Constants.POST_METHOD);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        proceedRequest(req, resp, Constants.GET_METHOD);
    }

    private void proceedRequest(HttpServletRequest request, HttpServletResponse response, String method) throws IOException {
        DevUtils.printRequestHeaders(request);

        String reqURI = request.getRequestURI();
        if (reqURI.contains(Constants.PARAMETER_KEY)) {
            reqURI = reqURI.replace(reqURI.substring(reqURI.lastIndexOf(Constants.PARAMETER_KEY)), "");
        }
        String commandValid = String.format(Constants.COMMAND_PATTERN, method, reqURI.substring(reqURI.lastIndexOf("/") + 1));
        String command = String.format(Constants.COMMAND_PATTERN, method, request.getHeader(Constants.COMMAND_HEADER));
        if (!command.equals(commandValid)) {
            System.out.println(command + " -- " + commandValid);
//            log.error(Constants.COMMAND_INVALID_ERROR);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, Constants.INVALID_COMMAND);
            return;
        }

        Command currentCommand = CommandFactory.getFactory().defineController(command);
        if (currentCommand == null) {
//            log.error(Constants.UNDEFINED_COMMAND_ERROR);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, Constants.UNSUPPORTED_COMMAND);
            return;
        }
        currentCommand.execute(request, response);

    }
}
