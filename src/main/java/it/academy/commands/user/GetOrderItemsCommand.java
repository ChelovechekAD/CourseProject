package it.academy.commands.user;

import it.academy.DTO.request.GetOrderItemsDTO;
import it.academy.DTO.response.OrderItemsDTO;
import it.academy.commands.Command;
import it.academy.services.OrderService;
import it.academy.services.impl.OrderServiceImpl;
import it.academy.utilities.Extractor;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static it.academy.utilities.Constants.GSON;

public class GetOrderItemsCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetOrderItemsDTO getOrderItemsDTO = Extractor.extractDTOFromRequest(request, new GetOrderItemsDTO());
        OrderService orderService = new OrderServiceImpl();
        OrderItemsDTO dto = orderService.getOrderItems(getOrderItemsDTO);
        String resp = GSON.toJson(dto);
        ResponseHelper.sendJsonResponse(response, resp);
    }
}
