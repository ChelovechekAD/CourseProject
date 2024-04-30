package it.academy.commands.ordercart;

import it.academy.DTO.request.DeleteItemFromCartDTO;
import it.academy.commands.Command;
import it.academy.services.CartService;
import it.academy.services.impl.CartServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

import static it.academy.utilities.Constants.GSON;

public class DeleteCartItemCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CartService cartService = new CartServiceImpl();
        String req = request.getReader().lines().collect(Collectors.joining());
        DeleteItemFromCartDTO dto = GSON.fromJson(req, DeleteItemFromCartDTO.class);
        cartService.deleteItemFromCart(dto);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
