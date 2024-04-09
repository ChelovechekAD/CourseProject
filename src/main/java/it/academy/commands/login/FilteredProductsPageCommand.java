package it.academy.commands.login;

import it.academy.DTO.response.ProductsDTO;
import it.academy.commands.Command;
import it.academy.services.ProductService;
import it.academy.services.impl.ProductServiceImpl;
import it.academy.utilities.Constants;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static it.academy.utilities.Constants.GSON;

public class FilteredProductsPageCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductService productService = new ProductServiceImpl();
        ProductsDTO productsDTO = productService
                .getAllExistProductByCategoryName(Long.parseLong(request.getParameter(Constants.CATEGORY_ID_PARAM_KEY)),
                        Integer.parseInt(request.getParameter(Constants.PAGE_NUM_PARAM_KEY)),
                        Integer.parseInt(request.getParameter(Constants.COUNT_PER_PAGE_PARAM_KEY)));
        String resp = GSON.toJson(productsDTO);
        System.out.println(resp);
        ResponseHelper.sendJsonResponse(response, resp);
    }
}
