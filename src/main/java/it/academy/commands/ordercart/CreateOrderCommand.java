package it.academy.commands.ordercart;

import it.academy.DTO.request.CreateOrderDTO;
import it.academy.commands.Command;
import it.academy.services.OrderService;
import it.academy.services.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

import static it.academy.utilities.Constants.GSON;

public class CreateOrderCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String req = request.getReader().lines().collect(Collectors.joining());
        System.out.println(req);
        CreateOrderDTO dto = GSON.fromJson(req, CreateOrderDTO.class);
        OrderService orderService = new OrderServiceImpl();
        orderService.createOrder(dto);
        response.setStatus(HttpServletResponse.SC_CREATED);

    }
}
