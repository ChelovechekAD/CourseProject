package it.academy.commands.admin;

import it.academy.DTO.request.UpdateOrderStatusDTO;
import it.academy.commands.Command;
import it.academy.services.OrderService;
import it.academy.services.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

import static it.academy.utilities.Constants.GSON;

public class ChangeOrderStatusCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String req = request.getReader().lines().collect(Collectors.joining());
        UpdateOrderStatusDTO dtoIn = GSON.fromJson(req, UpdateOrderStatusDTO.class);
        OrderService orderService = new OrderServiceImpl();
        orderService.changeOrderStatus(dtoIn);
    }
}
