package it.academy.commands.catalog;

import it.academy.DTO.request.RequestDataDetailsDTO;
import it.academy.DTO.response.ProductsDTO;
import it.academy.commands.Command;
import it.academy.services.ProductService;
import it.academy.services.impl.ProductServiceImpl;
import it.academy.utilities.Extractor;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static it.academy.utilities.Constants.GSON;

public class ProductsPageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductService productService = new ProductServiceImpl();
        ProductsDTO productsDTO = productService.getAllExistProducts(Extractor.extractDTOFromRequest(request, new RequestDataDetailsDTO()));
        String resp = GSON.toJson(productsDTO);
        ResponseHelper.sendJsonResponse(response, resp);
    }
}
