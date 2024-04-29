package it.academy.commands.admin;

import it.academy.DTO.request.RequestDataDetailsDTO;
import it.academy.DTO.request.UpdateOrderStatusDTO;
import it.academy.commands.Command;
import it.academy.exceptions.OrderNotFoundException;
import it.academy.services.OrderService;
import it.academy.services.impl.OrderServiceImpl;
import it.academy.utilities.Extractor;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.stream.Collectors;

import static it.academy.utilities.Constants.GSON;

public class ChangeOrderStatusCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String req = request.getReader().lines().collect(Collectors.joining());
        UpdateOrderStatusDTO dtoIn = GSON.fromJson(req, UpdateOrderStatusDTO.class);
        OrderService orderService = new OrderServiceImpl();
        try {
            orderService.changeOrderStatus(dtoIn);
        }catch (OrderNotFoundException e){
            ResponseHelper.sendResponseWithStatus(response, HttpStatus.SC_NOT_FOUND, e.getMessage());
        }
    }
}
