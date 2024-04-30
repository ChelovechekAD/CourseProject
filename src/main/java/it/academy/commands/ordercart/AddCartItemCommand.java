package it.academy.commands.ordercart;

import it.academy.DTO.request.AddToCartDTO;
import it.academy.commands.Command;
import it.academy.services.CartService;
import it.academy.services.impl.CartServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

import static it.academy.utilities.Constants.GSON;

public class AddCartItemCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CartService cartService = new CartServiceImpl();
        String req = request.getReader().lines().collect(Collectors.joining());
        AddToCartDTO addToCartDTO = GSON.fromJson(req, AddToCartDTO.class);
        cartService.addItemToCart(addToCartDTO);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
