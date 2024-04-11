package it.academy.commands.ordercart;

import it.academy.commands.Command;
import it.academy.exceptions.ProductNotFoundException;
import it.academy.exceptions.UserNotFoundException;
import it.academy.services.CartService;
import it.academy.services.impl.CartServiceImpl;
import it.academy.utilities.Constants;
import it.academy.utilities.Extractor;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static it.academy.utilities.Constants.GSON;

public class GetAllCartItemsCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Long value = Extractor.extractSingleParamFromRequest(request, Constants.USER_ID_PARAM_KEY, Long.class);
            CartService cartService = new CartServiceImpl();
            String resp = GSON.toJson(cartService.getAllCartByUserId(value));
            ResponseHelper.sendJsonResponse(response, resp);
        }catch (ProductNotFoundException | UserNotFoundException e){
            ResponseHelper.sendResponseWithStatus(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
}
