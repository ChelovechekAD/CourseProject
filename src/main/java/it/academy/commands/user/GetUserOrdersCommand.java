package it.academy.commands.user;

import it.academy.DTO.request.GetUserOrderPageDTO;
import it.academy.DTO.response.OrdersDTO;
import it.academy.commands.Command;
import it.academy.services.OrderService;
import it.academy.services.impl.OrderServiceImpl;
import it.academy.utilities.Extractor;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static it.academy.utilities.Constants.GSON;

public class GetUserOrdersCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetUserOrderPageDTO dtoIn = Extractor.extractDTOFromRequest(request, new GetUserOrderPageDTO());
        OrderService orderService = new OrderServiceImpl();
        OrdersDTO dtoOut = orderService.getListOfUserOrders(dtoIn);
        ResponseHelper.sendJsonResponse(response, GSON.toJson(dtoOut));

    }
}
