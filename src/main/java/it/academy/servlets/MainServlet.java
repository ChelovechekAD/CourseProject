package it.academy.servlets;

import it.academy.commands.Command;
import it.academy.commands.factory.CommandEnum;
import it.academy.commands.factory.CommandFactory;
import it.academy.utilities.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "MainServlet", urlPatterns = "/api/*")
public class MainServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        proceedRequest(req, resp, Constants.POST_METHOD);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        proceedRequest(req, resp, Constants.GET_METHOD);
    }

    private void proceedRequest(HttpServletRequest request, HttpServletResponse response, String method) throws IOException {
        CommandEnum command = (CommandEnum) request.getAttribute(Constants.COMMAND_ENUM);
        Command currentCommand = CommandFactory.getFactory().defineController(command);
        currentCommand.execute(request, response);

    }
}
