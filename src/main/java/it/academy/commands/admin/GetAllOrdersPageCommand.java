package it.academy.commands.admin;

import it.academy.DTO.request.RequestDataDetailsDTO;
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

public class GetAllOrdersPageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestDataDetailsDTO requestDataDetailsDTO = Extractor.extractDTOFromRequest(request, new RequestDataDetailsDTO());
        OrderService orderService = new OrderServiceImpl();
        OrdersDTO ordersDTO = orderService.getListOfOrders(requestDataDetailsDTO);
        String resp = GSON.toJson(ordersDTO);
        ResponseHelper.sendJsonResponse(response, resp);
    }
}
